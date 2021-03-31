package kz.kazgisa.service;

import kz.kazgisa.data.dto.SearchCriteriaDto;
import kz.kazgisa.data.dto.TaskDto;
import kz.kazgisa.data.dto.report.ReportByExecutorDto;
import kz.kazgisa.data.entity.dict.Subservice;
import kz.kazgisa.data.entity.user.UserRole;
import kz.kazgisa.data.enums.SearchOperation;
import kz.kazgisa.data.repositories.dict.SubserviceRepository;
import kz.kazgisa.data.repositories.user.UserRepository;
import kz.kazgisa.data.repositories.user.UserRoleRepository;
import kz.kazgisa.mapper.TaskMapper;
import kz.kazgisa.utils.CommonUtils;
import kz.kazgisa.utils.CustomDateUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricDetail;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstanceQuery;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.impl.history.event.HistoricVariableUpdateEventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HistoryCustomService {
    private final HistoryService historyService;
    private final RuntimeService runtimeService;
    private final UserService userService;
    private final SubserviceRepository subserviceRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    public HistoryCustomService(HistoryService historyService, RuntimeService runtimeService, UserService userService, SubserviceRepository subserviceRepository, UserRoleRepository userRoleRepository, UserRepository userRepository) {
        this.historyService = historyService;
        this.runtimeService = runtimeService;
        this.userService = userService;
        this.subserviceRepository = subserviceRepository;
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
    }

    public Page<TaskDto> getUserTasks(String assignee, Boolean finished, Pageable pageable) {
        //List<Subservice> subservices = userService.getCurrentUserSubservices();
        HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
        if (finished) {
            query = query.finished();
        } else {
            query = query.unfinished();
        }
        if (assignee != null) {
            query = query.taskAssignee(assignee);
        }
        /*if(!subservices.isEmpty()) {
            for (Subservice service : subservices) {
                query = query.processDefinitionKey(service.getService().getCode());
            }
        }*/
        long count = query.count();
        int from = pageable.getPageSize() * (pageable.getPageNumber());
        return new PageImpl<>(query.orderByHistoricTaskInstanceEndTime().desc().listPage(from, pageable.getPageSize()).stream()
                .map(TaskMapper::mapToDto)
                .map(taskDto -> {
                            try {
                                List<HistoricVariableInstance> h = historyService.createHistoricVariableInstanceQuery()
                                        .processInstanceId(taskDto.getProcessInstanceId()).list();
                                Map<String, Object> m = new HashMap<>();
                                for (HistoricVariableInstance hv : h) {
                                    m.put(hv.getName(), hv.getValue());
                                }
                                taskDto.setContent(m);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return taskDto;
                        }
                ).collect(Collectors.toList()), pageable, count);
    }

    public Page<TaskDto> filterUserTasks(List<SearchCriteriaDto> list, Pageable pageable) {
        HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
        for (SearchCriteriaDto criteria : list) {
            String key = criteria.getKey();
            if(criteria.getValue() != null && !criteria.getValue().equals("")) {
                if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                    if (key.equals("assignee")) {
                        query = query.taskAssignee(criteria.getValue().toString());
                    } else if (key.equals("finished")) {
                        boolean finished = (boolean) criteria.getValue();
                        if (finished)
                            query = query.finished();
                        else
                            query = query.unfinished();
                    } else if (key.equals("createTime")) {
                        Date date = new Date((long) criteria.getValue());
                        Date dateStart = CustomDateUtils.getDayStart(date);
                        Date dateEnd = CustomDateUtils.getDayEnd(date);
                        query = query.startedAfter(dateStart);
                        query = query.startedBefore(dateEnd);
                    } else if (key.equals("dueDate")) {
                        Date date = new Date((long) criteria.getValue());
                        Date dateStart = CustomDateUtils.getDayStart(date);
                        Date dateEnd = CustomDateUtils.getDayEnd(date);
                        query = query.processVariableValueGreaterThanOrEquals("planEndDate", dateStart);
                        query = query.processVariableValueLessThanOrEquals("planEndDate", dateEnd);
                    } else if (key.equals("appId") || key.equals("subserviceId")) {
                        query = query.processVariableValueEquals(key, Integer.valueOf(criteria.getValue().toString()));
                    } else if (key.equals("approved") && criteria.getValue() != null) {
                        query = query.processVariableValueEquals(key, Boolean.valueOf(criteria.getValue().toString()));
                    } else if (key.equals("revoked") && criteria.getValue() != null) {
                        query = query.processVariableValueEquals(key, Boolean.valueOf(criteria.getValue().toString()));
                    } else {
                        query = query.processVariableValueEquals(key, criteria.getValue().toString());
                    }
                } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
                    if (key.equals("assignee"))
                        query = query.taskAssigneeLike(criteria.getValue().toString().toLowerCase());
                    else {
                        query = query.processVariableValueLike(key, "%" + criteria.getValue().toString().toLowerCase() + "%");
                    }

                } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
                    if (key.equals("createTime")) {
                        query = query.startedAfter(new Date((long) criteria.getValue()));
                    } else if (key.equals("dueDate")) {
                        query = query.processVariableValueGreaterThanOrEquals("planEndDate", new Date((long) criteria.getValue()));
                    } else {
                        query = query.processVariableValueGreaterThanOrEquals(key, criteria.getValue());
                    }

                } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
                    if (key.equals("assignee"))
                        query = query.taskAssignee(criteria.getValue().toString());
                    else if (key.equals("createTime")) {
                        query = query.startedBefore(new Date((long) criteria.getValue()));
                    } else if (key.equals("dueDate")) {
                        query = query.processVariableValueLessThanOrEquals("planEndDate", new Date((long) criteria.getValue()));
                    } else {
                        query = query.processVariableValueLessThanOrEquals(key, criteria.getValue());
                    }
                } else if (criteria.getOperation().equals(SearchOperation.BETWEEN)) {
                    if(criteria.getValue2() != null && !criteria.getValue2().equals("")) {
                        if (key.equals("createTime")) {
                            query = query.startedAfter(new Date((long) criteria.getValue()));
                            query = query.startedBefore(new Date((long) criteria.getValue2()));
                        } else if (key.equals("dueDate")) {
                            query = query.processVariableValueGreaterThanOrEquals("planEndDate", new Date((long) criteria.getValue()));
                            query = query.processVariableValueLessThanOrEquals("planEndDate", new Date((long) criteria.getValue2()));
                        }
                    }
                }
            }
        }
        long count = query.count();
        int from = pageable.getPageSize() * (pageable.getPageNumber());
        return new PageImpl<>(query.orderByHistoricTaskInstanceEndTime().desc().listPage(from, pageable.getPageSize()).stream()
                .map(TaskMapper::mapToDto)
                .map(taskDto -> {
                            try {
                                List<HistoricVariableInstance> h = historyService.createHistoricVariableInstanceQuery()
                                        .processInstanceId(taskDto.getProcessInstanceId()).list();
                                Map<String, Object> m = new HashMap<>();
                                for (HistoricVariableInstance hv : h) {
                                    if (!hv.getName().toLowerCase().contains("files") && !hv.getName().equals("message"))
                                        m.put(hv.getName(), hv.getValue());
                                }
                                taskDto.setContent(m);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return taskDto;
                        }
                ).collect(Collectors.toList()), pageable, count);
    }

    // TODO
    public Page<TaskDto> filterUserTasksForReport(List<SearchCriteriaDto> list, Pageable pageable) {
        HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
        for (SearchCriteriaDto criteria : list) {
            String key = criteria.getKey();
            if(criteria.getValue() != null && !criteria.getValue().toString().equals("")) {
                if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                    if (key.equals("assignee"))
                        query = query.taskAssignee(criteria.getValue().toString());
                    else if (key.equals("finished")) {
                        boolean finished = (boolean) criteria.getValue();
                        if (finished)
                            query = query.finished();
                        else
                            query = query.unfinished();
                    } else if (key.equals("dueDate")) {
                        query = query.taskDueDate(new Date((long) criteria.getValue()));
                    } else if (key.equals("appId") || key.equals("subserviceId")) {
                        query = query.processVariableValueEquals(key, Integer.valueOf(criteria.getValue().toString()));
                    } else {
                        query = query.processVariableValueEquals(key, criteria.getValue().toString());
                    }

                } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
                    if (key.equals("assignee")) {
                        query = query.taskAssigneeLike(criteria.getValue().toString().toLowerCase());
                    } else {
                        query = query.processVariableValueLike(key, "%" + criteria.getValue().toString().toLowerCase() + "%");
                    }

                } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
                    if (key.equals("dueDate")) {
                        query = query.taskDueAfter(new Date((long) criteria.getValue()));
                    }

                } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
                    if (key.equals("dueDate")) {
                        query = query.taskDueBefore(new Date((long) criteria.getValue()));
                    }
                }
            }
        }
        long count = query.count();
        int from = pageable.getPageSize() * (pageable.getPageNumber());
        return new PageImpl<>(query.orderByHistoricActivityInstanceStartTime().desc().listPage(from, pageable.getPageSize()).stream()
                .map(TaskMapper::mapToDto)
                .map(taskDto -> {
                            try {
                                String instanceId = taskDto.getProcessInstanceId() != null ? taskDto.getProcessInstanceId() : "";
                                List<HistoricVariableInstance> h = historyService.createHistoricVariableInstanceQuery()
                                        .processInstanceId(instanceId).list();
                                Map<String, Object> m = new HashMap<>();
                                for (HistoricVariableInstance hv : h) {
                                    if (!hv.getName().toLowerCase().contains("files") && !hv.getName().equals("message"))
                                        m.put(hv.getName(), hv.getValue());
                                }
                                taskDto.setContent(m);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return taskDto;
                        }
                ).collect(Collectors.toList()), pageable, count);
    }

    public Long getUserTasksCount(List<Subservice> list, String assignee, Boolean finished) {
        HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
        if (finished) {
            query = query.finished();
        } else {
            query = query.unfinished();
        }
        if (assignee != null) {
            query = query.taskAssignee(assignee);
        }
       /* if(!list.isEmpty()) {
            for (Subservice service : list) {
                query = query.processDefinitionKey(service.getService().getCode());
            }
        }*/
        return query.count();
    }

    public Map<String, Object> getUserTasksForm(String id) {
        HistoricTaskInstance h = historyService.createHistoricTaskInstanceQuery().taskId(id).singleResult();
        return runtimeService.getVariables(h.getExecutionId());
    }

    public List<TaskDto> getAppHistory(Long id, Long subserviceId) {
        HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
        if (subserviceId != null) {
            Subservice subservice = subserviceRepository.findById(subserviceId).get();
            query = query.processDefinitionKey(subservice.getCode() != null ? subservice.getCode() : subservice.getService().getCode());
        }
        return query.processInstanceBusinessKey(String.valueOf(id))
                .orderByHistoricTaskInstanceEndTime().asc().list().stream().map(TaskMapper::mapToDto)
                .peek(taskDto -> {
                            try {
                                List<HistoricVariableInstance> h = historyService.createHistoricVariableInstanceQuery()
                                        .processInstanceId(taskDto.getProcessInstanceId()).list();
                                Map<String, Object> m = new HashMap<>();
                                for (HistoricVariableInstance hv : h) {
                                    m.put(hv.getName(), hv.getValue());
                                }
                                taskDto.setContent(m);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                )
                .collect(Collectors.toList());
    }

    public List<HistoricDetail> getHistoryTaskVariables(String id) {
        HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
        HistoricTaskInstance historicTaskInstance = query.taskId(id).singleResult();

        List<HistoricDetail> historicDetails = historyService.createHistoricDetailQuery()
                .processInstanceId(historicTaskInstance.getProcessInstanceId())
                .variableUpdates()
                .activityInstanceId(historicTaskInstance.getActivityInstanceId())
                .list();
        return historicDetails;
    }

    public List<HistoricVariableUpdateEventEntity> getTaskById(String id) {
        HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
        HistoricTaskInstance historicTaskInstance = query.taskId(id).singleResult();

        List<HistoricDetail> historicDetails = historyService.createHistoricDetailQuery()
                .processInstanceId(historicTaskInstance.getProcessInstanceId())
                .variableUpdates()
                .activityInstanceId(historicTaskInstance.getActivityInstanceId())
                .list();
        List<HistoricVariableUpdateEventEntity> changes = new ArrayList<HistoricVariableUpdateEventEntity>();
        for (HistoricDetail detail : historicDetails) {
            HistoricVariableUpdateEventEntity variable = (HistoricVariableUpdateEventEntity) detail;
            if (variable.getVariableName().equals("orderText"))
                changes.add(variable);
        }
        return changes;
    }


    public List<ReportByExecutorDto> getTaskStats(Long from, Long to) {
        // get executors
        List<UserRole> userRoleList = userRoleRepository.findByRoleNameContaining("EXECUTOR");
        // for each call
        List<ReportByExecutorDto> report = new ArrayList<>();
        for (UserRole userRole : userRoleList) {
            if (userRole.getUsername() != null) {
                Set<String> procDefs = new HashSet<>();
                historyService.createHistoricTaskInstanceQuery()
                        .processFinished()
                        .finishedAfter(new Date(from))
                        .finishedBefore(new Date(to))
                        .taskAssignee(userRole.getUsername())
                        .list()
                        .forEach(hT -> procDefs.add(hT.getProcessInstanceId()));
                userRole.setUser(userRepository.findFirstByUsername(userRole.getUsername()));
                report.add(new ReportByExecutorDto(userRole, procDefs.size()));
            }
        }
        Comparator<ReportByExecutorDto> compareById = Comparator.comparing(ReportByExecutorDto::getCount);
        report.sort(Collections.reverseOrder(compareById));
        return report;
    }

    public Date getFinishDate(Long appId) {
        Date endDate = null;
        HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery()
                .processFinished().processInstanceBusinessKey(String.valueOf(appId))
                .orderByHistoricTaskInstanceEndTime().desc().list().stream().findFirst().orElse(null);
        if(task != null) {
            endDate = task.getEndTime();
        }
        return endDate;
    }
}
