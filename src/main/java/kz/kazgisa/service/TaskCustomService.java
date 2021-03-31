package kz.kazgisa.service;

import camundafeel.de.odysseus.el.ExpressionFactoryImpl;
import camundafeel.de.odysseus.el.util.SimpleContext;
import camundafeel.de.odysseus.el.util.SimpleResolver;
import camundafeel.javax.el.ExpressionFactory;
import camundafeel.javax.el.ValueExpression;
import kz.kazgisa.data.dto.SearchCriteriaDto;
import kz.kazgisa.data.dto.TaskDto;
import kz.kazgisa.data.dto.UserDto;
import kz.kazgisa.data.entity.App;
import kz.kazgisa.data.entity.dict.Subservice;
import kz.kazgisa.data.entity.user.User;
import kz.kazgisa.data.entity.user.UserRole;
import kz.kazgisa.data.enums.SearchOperation;
import kz.kazgisa.data.repositories.AppRepository;
import kz.kazgisa.data.repositories.user.UserRepository;
import kz.kazgisa.data.repositories.user.UserRoleRepository;
import kz.kazgisa.mapper.TaskMapper;
import kz.kazgisa.mapper.UserMapper;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.IdentityLink;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.camunda.bpm.engine.variable.type.ValueType;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TaskCustomService {

    private final TaskService taskService;
    private final UserService userService;
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RepositoryService repositoryService;
    Logger log = LoggerFactory.getLogger(TaskCustomService.class);


    public TaskCustomService(TaskService taskService, UserService userService, UserRoleRepository userRoleRepository, UserRepository userRepository, RepositoryService repositoryService) {
        this.taskService = taskService;
        this.userService = userService;
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.repositoryService = repositoryService;
    }

    public Page<TaskDto> getUserTasks(String assignee, String role, String owner, Pageable page) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Subservice> subservices = userService.getCurrentUserSubservices();
        if (assignee != null) {
            taskQuery = taskQuery.taskAssignee(assignee);
        }
        if (role != null) {
            taskQuery = taskQuery.taskCandidateGroupIn(Arrays.asList(role.split(",")));
        }
        if (owner != null) {
            taskQuery = taskQuery.taskOwner(owner);
        }
        if (!subservices.isEmpty()) {
            taskQuery = taskQuery.processDefinitionKeyIn(subservices.stream()
                    .map(s -> s.getCode() != null ? s.getCode() : s.getService().getCode()).toArray(String[]::new));
        }
        int from = page.getPageSize() * (page.getPageNumber());
        return new PageImpl<>(taskQuery.orderByTaskCreateTime().desc().listPage(from, page.getPageSize()).stream().map(TaskMapper::mapToDto)
                .peek(taskDto -> taskDto.setContent(taskService.getVariables(taskDto.getId())))
                .collect(Collectors.toList()), page, taskQuery.count());
    }

    public Page<TaskDto> filterUserTasks(List<SearchCriteriaDto> list, Pageable page, String sort) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Subservice> subservices = userService.getCurrentUserSubservices();
        if (!subservices.isEmpty()) {
            taskQuery = taskQuery.processDefinitionKeyIn(subservices.stream()
                    .map(s -> s.getCode() != null ? s.getCode() : s.getService().getCode()).toArray(String[]::new));
        }
        for (SearchCriteriaDto criteria : list) {
            String key = criteria.getKey();
            if(criteria.getValue() != null && !criteria.getValue().toString().equals("")) {
                if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                    if (key.equals("assignee"))
                        taskQuery = taskQuery.taskAssignee(criteria.getValue().toString());
                    else if (key.equals("createDateTime")) {
                        taskQuery = taskQuery.taskCreatedOn(new Date((long) criteria.getValue()));
                    } else if (key.equals("dueDate")) {
                        taskQuery = taskQuery.dueDate(new Date((long) criteria.getValue()));
                    } else if (key.equals("appId") || key.equals("subserviceId")) {
                        taskQuery = taskQuery.processVariableValueEquals(key, Integer.valueOf(criteria.getValue().toString()));
                    } else {
                        taskQuery = taskQuery.processVariableValueEquals(key, criteria.getValue().toString());
                    }
                } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
                    if (key.equals("assignee"))
                        taskQuery = taskQuery.taskAssigneeLike(criteria.getValue().toString().toLowerCase());
                    else {
                        taskQuery = taskQuery.processVariableValueLike(key, "%" + criteria.getValue().toString().toLowerCase() + "%");
                    }

                } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
                    if (key.equals("createDateTime")) {
                        taskQuery = taskQuery.taskCreatedAfter(new Date((long) criteria.getValue()));
                    } else if (key.equals("dueDate")) {
                        taskQuery = taskQuery.dueAfter(new Date((long) criteria.getValue()));
                    } else {
                        taskQuery = taskQuery.processVariableValueGreaterThanOrEquals(key, criteria.getValue());
                    }

                } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
                    if (key.equals("assignee"))
                        taskQuery = taskQuery.taskAssignee(criteria.getValue().toString());
                    else if (key.equals("createDateTime")) {
                        taskQuery = taskQuery.taskCreatedBefore(new Date((long) criteria.getValue()));
                    } else if (key.equals("dueDate")) {
                        taskQuery = taskQuery.dueBefore(new Date((long) criteria.getValue()));
                    } else {
                        taskQuery = taskQuery.processVariableValueLessThanOrEquals(key, criteria.getValue());
                    }
                }
            }
        }

        int from = page.getPageSize() * (page.getPageNumber());
        Stream<Task> stream;
        if (sort != null && sort.equals("planEndDate")) {
            stream = taskQuery.orderByProcessVariable("planEndDate", ValueType.DATE).asc().listPage(from, page.getPageSize()).stream();
        } else {
            stream = taskQuery.orderByTaskCreateTime().desc().listPage(from, page.getPageSize()).stream();
        }

        Page<TaskDto> taskPage = new PageImpl<>(stream.map(TaskMapper::mapToDto)
                .peek(taskDto -> taskDto.setContent(
                        taskService.getVariables(taskDto.getId())
                                .entrySet().stream()
                                .filter(x -> !x.getKey().toLowerCase().contains("files")
                                        && !x.getKey().equals("message")
                                        && !x.getKey().toLowerCase().contains("text")
                                        && x.getValue() != null)
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))))
                .collect(Collectors.toList()), page, taskQuery.count());
        for(TaskDto taskDto : taskPage.getContent()) {
            taskDto.getContent().put("role", getCurrentRoleOfTask(taskDto.getId()));
        }
        return taskPage;
    }


    public Long getUserTasksCount(List<Subservice> list, String assignee, String role, String owner) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        if (assignee != null) {
            taskQuery = taskQuery.taskAssignee(assignee);
        }
        if (role != null) {
            taskQuery = taskQuery.taskCandidateGroupIn(Arrays.asList(role.split(",")));
        }
        if (owner != null) {
            taskQuery = taskQuery.taskOwner(owner);
        }
        if (!list.isEmpty()) {
            taskQuery = taskQuery.processDefinitionKeyIn(list.stream()
                    .map(s -> s.getCode() != null ? s.getCode() : s.getService().getCode()).toArray(String[]::new));
        }
        return taskQuery.count();
    }

    /*public TaskDto completeTask(String id, Map<String, Object> map) throws ParseException {
        Task t = taskService.createTaskQuery().taskId(id).singleResult();
        String pi = t.getProcessInstanceId();
        taskService.complete(id,map);
        Task task = taskService.createTaskQuery().active().processInstanceId(pi).singleResult();
        if(task == null)
            return null;
        String type= (String) taskService.getVariable(task.getId(),"type");
        String executor = (String) taskService.getVariable(task.getId(),"executor");
        String owner = (String) taskService.getVariable(task.getId(),"owner");
        String org = (String) taskService.getVariable(task.getId(), "org");
        String role = (String) taskService.getVariable(task.getId(),"role");
        log.info("org is "+org);
        log.info("role is "+role);
        if(org != null) {
            Organization organization = organizationRepository.findFirstByOrgType(OrgType.valueOf(org));
            UserRole exe, owe;
            if(organization != null) {
                List<String> users = userService.getUsersByOrganizationId(organization.getId()).stream().map(User::getUsername).collect(Collectors.toList());
                if(role == null) {
                    role = RoleType.EXECUTOR.name();
                }
                exe = userRoleRepository.findFirstByUsernameInAndRoleName(users, role);
                if (exe != null)
                    executor = exe.getUsername();
                owe = userRoleRepository.findFirstByUsernameInAndRoleName(users, RoleType.HEAD.name());
                if (owe != null)
                    owner = owe.getUsername();
            }else if(role!=null) { //TODO: it should not be here! remove this block later
                log.info("setting by role ");
                exe = userRoleRepository.findFirstByRoleName(role);
                if(exe != null) {
                    log.info("role exists!");
                    executor = exe.getUsername();
                    owner = exe.getUsername();
                }
            }
        } else if(role != null) {
            UserRole exe = userRoleRepository.findFirstByRoleNameAndCurrentTrue(role);
            if(exe != null) {
                log.info("role exists!");
                executor = exe.getUsername();
                owner = exe.getUsername();
            }
        }

        if (task.getAssignee() == null || task.getAssignee().isEmpty()) {
            if (type == null)
                return null;
            log.info("setting task assignee (type executor owner)"+type +" "+executor+" "+owner);
            if (type.startsWith("TASK")) {
                if (executor != null)
                    task.setAssignee(executor);
                taskService.saveTask(task);
            } else if (type.equals("SIGN")) {
                if (owner != null)
                    task.setAssignee(owner);
                else if(executor != null)
                    task.setAssignee(executor);
                taskService.saveTask(task);
            }
        }


        if(map.get("dueDate") != null) {
            DateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            task.setDueDate(f.parse((String)map.get("dueDate")));
        }
        return TaskMapper.mapToDto(task);   
    }*/

    public TaskDto completeTask(String id, Map<String, Object> map, String assignee) throws ParseException {
        Task t = taskService.createTaskQuery().taskId(id).singleResult();
        String pi = t.getProcessInstanceId();
        taskService.complete(id, map);
        Task task = taskService.createTaskQuery().active().processInstanceId(pi).singleResult();
        if (task == null) {
            return null;
        }

        String mio = (String) taskService.getVariable(task.getId(), "mio");
        List<IdentityLink> list = taskService.getIdentityLinksForTask(task.getId());
        for (IdentityLink identityLink : list) {
            String group = identityLink.getGroupId();
            if (group != null) {

                if (assignee != null && !assignee.equals("")) {
                    if (userService.userHasRole(assignee, group)) {
                        task.setAssignee(assignee);
                        break;
                    }
                }

                UserRole userRole;
                if (mio == null || !mio.equals("true")) {
                    userRole = userRoleRepository.findFirstByRoleNameAndCurrentTrue(group);
                } else {
                    Long regionId = ((Number) taskService.getVariable(task.getId(), "regionId")).longValue();
                    userRole = userRoleRepository.findByRoleNameAndCurrentTrue(group)
                            .stream()
                            .filter(uR -> {
                                User user = userRepository.findFirstByUsername(uR.getUsername());
                                return Objects.equals(user.getRegionId(), regionId);
                            }).findFirst().orElse(null);
                    if (userRole == null) {
                        userRole = userRoleRepository.findByRoleName(group)
                                .stream()
                                .filter(uR -> {
                                    User user = userRepository.findFirstByUsername(uR.getUsername());
                                    return Objects.equals(user.getRegionId(), regionId);
                                }).findFirst().orElse(null);
                    }
                }
                if (userRole != null && userRole.getUsername() != null) {
                    task.setAssignee(userRole.getUsername());
                }
            }
        }
        if (map.get("dueDate") != null) {
            DateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            task.setDueDate(f.parse((String) map.get("dueDate")));
        }
        taskService.saveTask(task);
        return TaskMapper.mapToDto(task);
    }

    public Map<String, Object> getTaskVariables(String id) {
        return taskService.getVariables(id);
    }

    List<UserRole> getAllUserRolesByGroup(String group, Task task) {
        List<UserRole> userRoles;
        String mio = (String) taskService.getVariable(task.getId(), "mio");
        if (mio == null || !mio.equals("true")) {
            userRoles = userRoleRepository.findByRoleName(group);
        } else {
            Long regionId = ((Number) taskService.getVariable(task.getId(), "regionId")).longValue();
            userRoles = userRoleRepository.findByRoleName(group)
                    .stream()
                    .filter(uR -> {
                        User user = userRepository.findFirstByUsername(uR.getUsername());
                        return Objects.equals(user.getRegionId(), regionId);
                    }).collect(Collectors.toList());
            if (userRoles == null || userRoles.size() == 0) {
                userRoles = userRoleRepository.findByRoleName(group)
                        .stream()
                        .filter(uR -> {
                            User user = userRepository.findFirstByUsername(uR.getUsername());
                            return Objects.equals(user.getRegionId(), regionId);
                        }).collect(Collectors.toList());
            }
        }
        return userRoles;
    }

    public TaskDto updateTask(String id, TaskDto taskDto) {
        Task task = taskService.createTaskQuery().taskId(id).singleResult();
        if (taskDto.getAssignee() != null) {
//            try {
//                taskService.claim(id, taskDto.getAssignee());
//            } catch (Exception e) {}
            task.setAssignee(taskDto.getAssignee());
        }
        if (taskDto.getDueDate() != null) {
            task.setDueDate(taskDto.getDueDate());
        }
        if (taskDto.getOwner() != null) {
            task.setOwner(taskDto.getOwner());
        }

        TaskDto dto = TaskMapper.mapToDto(task);
        taskService.setVariables(task.getId(), taskDto.getContent());
        taskService.saveTask(task);
        return dto;
    }

    public void setAssignees(String currentAssignee, String newAssignee) {
        List<SearchCriteriaDto> searchCriteria = new ArrayList<>();
        searchCriteria.add(new SearchCriteriaDto("assignee", currentAssignee, SearchOperation.EQUAL));
        Page<TaskDto> taskPage = filterUserTasks(searchCriteria, PageRequest.of(0, 10000), null);

        for(TaskDto taskDto : taskPage.getContent()) {
            taskDto.setAssignee(newAssignee);
            updateTask(taskDto.getId(), taskDto);
        }
    }

    public TaskDto getUserTaskById(String id) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        Task task = taskQuery.taskId(id).singleResult();
        TaskDto dto = TaskMapper.mapToDto(task);
        dto.setContent(taskService.getVariables(dto.getId()));
        dto.getContent().put("role", getCurrentRoleOfTask(dto.getId()));
        return dto;
    }

    public Long getUserUnassignedTasks(List<Subservice> list) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        if (!list.isEmpty()) {
            taskQuery = taskQuery.processDefinitionKeyIn(list.stream().map(s -> s.getService().getCode()).toArray(String[]::new));
        }
        return taskQuery.taskUnassigned().count();
    }

    public List<UserTask> getNextTasks(String processDefinitionId, String taskDefinitionKey, Map<String, Object> vars) {
        BpmnModelInstance modelInstance = repositoryService.getBpmnModelInstance(processDefinitionId);
        ModelElementInstance instance = modelInstance.getModelElementById(taskDefinitionKey);
        FlowNode flowNode = (FlowNode) instance;
        return getOutgoingTask(flowNode, vars);
    }

    private List<UserTask> getOutgoingTask(FlowNode node, Map<String, Object> vars) {
        List<UserTask> possibleTasks = new ArrayList<>();
        for (SequenceFlow sf : node.getOutgoing()) {
            if (sf.getName() != null) {
                log.info("Entering flow node {}", sf.getName());
            }
            boolean next = true;
            if (sf.getConditionExpression() != null) {
                ExpressionFactory factory = new ExpressionFactoryImpl();
                SimpleContext context = new SimpleContext(new SimpleResolver());

                log.info("Generating map vars {}", vars.toString());
                for (Map.Entry<String, Object> v : vars.entrySet()) {
                    if (v.getValue() instanceof Boolean) {
                        factory.createValueExpression(context, "${" + v.getKey() + "}", Boolean.class).setValue(context, v.getValue());
                    } else if (v.getValue() instanceof java.util.Date) {
                        factory.createValueExpression(context, "${" + v.getKey() + "}", java.util.Date.class).setValue(context, v.getValue());
                    } else {
                        factory.createValueExpression(context, "${" + v.getKey() + "}", String.class).setValue(context, v.getValue());
                    }
                }
                ValueExpression expr1 = factory.createValueExpression(context, sf.getConditionExpression().getTextContent(), boolean.class);

                next = (Boolean) expr1.getValue(context);
                log.info("Evaluating expression {} to result {}", sf.getConditionExpression().getTextContent(), expr1.getValue(context));

            }

            if (next && sf.getTarget() != null) {
                if (sf.getTarget() instanceof UserTask) {
                    log.info("Found next user task {}", sf.getTarget().getName());
                    possibleTasks.add((UserTask) sf.getTarget());
                    break;
                }

                possibleTasks.addAll(getOutgoingTask(sf.getTarget(), vars));
            }

        }
        return possibleTasks;
    }

    public List<UserDto> getNextAssignees(String id, Map map) {
        List<UserRole> assignees = new ArrayList<>();
        List<UserDto> users = new ArrayList<>();
        try {
            Task task = taskService.createTaskQuery().taskId(id).singleResult();
            String taskDefinitionKey = task.getTaskDefinitionKey();
            String processDefinitionId = task.getProcessDefinitionId();
            List<UserTask> nextTasks = getNextTasks(processDefinitionId, taskDefinitionKey, map);
            for (UserTask userTask : nextTasks) {
                if (userTask.getCamundaCandidateGroups() != null) {
                    List<UserRole> userRoles = getAllUserRolesByGroup(userTask.getCamundaCandidateGroups(), task);
                    assignees.addAll(userRoles);
                }
            }
            for (UserRole assignee : assignees) {
                if (assignee.getUsername() != null) {
                    User user = userService.getUserByUserName(assignee.getUsername());
                    if(user != null) {
                        UserDto userDto = UserMapper.mapToDto(user);
                        userDto.setCurrent(assignee.getCurrent());
                        users.add(userDto);
                    }
                }
            }
        } catch (Exception e) {
        }
        return users;
    }

    public String getCurrentRoleOfTask(String id) {
        List<IdentityLink> list = taskService.getIdentityLinksForTask(id);
        for (IdentityLink identityLink : list) {
            String group = identityLink.getGroupId();
            return group;
        }
        return null;
    }

    public TaskDto getTaskByBusinessKey(Long appId) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery = taskQuery.processVariableValueEquals("appId", Integer.valueOf(appId.toString())).active();
        List<TaskDto> tasks = taskQuery.listPage(0, 1).stream().map(TaskMapper::mapToDto)
                .peek(taskDto -> taskDto.setContent(taskService.getVariables(taskDto.getId())))
                .collect(Collectors.toList());
        if(tasks != null && tasks.size() > 0) {
            return tasks.get(0);
        }
        return null;
    }
}
