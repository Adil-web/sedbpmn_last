package kz.kazgisa.service;

import kz.kazgisa.data.entity.App;
import kz.kazgisa.data.entity.AppFile;
import kz.kazgisa.data.enums.FileCategory;
import kz.kazgisa.data.repositories.AppFileRepository;
import kz.kazgisa.data.repositories.AppRepository;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProcessCustomService {

    private final RuntimeService runtimeService;
    private final HistoryService historyService;
    private final TaskService taskService;
    private final AppService appService;
    private final AppRepository appRepository;
    private final AppFileRepository appFileRepository;

    public ProcessCustomService(RuntimeService runtimeService,
                                HistoryService historyService,
                                TaskService taskService,
                                AppService appService,
                                AppRepository appRepository,
                                AppFileRepository appFileRepository) {
        this.runtimeService = runtimeService;
        this.historyService = historyService;
        this.taskService = taskService;
        this.appService = appService;
        this.appRepository = appRepository;
        this.appFileRepository = appFileRepository;
    }

    @Transactional
    public Map<String, Object> updateProcess(String id, Map<String, Object> variables) throws ParseException {

        runtimeService.setVariables(id, variables);
        Task task = taskService.createTaskQuery().active().processInstanceId(id).singleResult();
        if (task != null) {
            String type = (String) taskService.getVariable(task.getId(), "type");
            if (variables.get("executor") != null && type.equals("TASK"))
                task.setAssignee((String) variables.get("executor"));
            else if (variables.get("owner") != null && type.equals("SIGN"))
                task.setAssignee((String) variables.get("owner"));
            if (variables.get("dueDate") != null) {
                DateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                task.setDueDate(f.parse((String) variables.get("dueDate")));
            }
            if (variables.get("owner") != null) {
                task.setOwner((String) variables.get("owner"));
            }
            taskService.saveTask(task);
        }
        Map<String, Object> v = runtimeService.getVariables(id);
        if (v.get("control") != null && v.get("appId") != null) {
            long appId = ((Number) v.get("appId")).longValue();
            App app = appService.getById(appId);
            app.setControl((String) v.get("control"));
            appService.save(app);
        }
        return v;
    }

    public void deleteProcessApp(String processInstanceId, Long appId) {
        deleteProcess(processInstanceId);
        if(appId != null) {
            appRepository.deleteApp(appId);
        }
    }

    public void restartProcessApp(String processInstanceId, Long appId) {
        deleteProcess(processInstanceId);

        App app = appService.getById(appId);
        app.setOpen(false);
        appRepository.save(app);

        List<AppFile> appFiles = appFileRepository.findByAppId(appId);
        List<AppFile> filesToDelete = new ArrayList<>();
        for (AppFile file : appFiles) {
            if (file.getFileCategory() == FileCategory.RESULT_FILE ||
                    file.getFileCategory() == FileCategory.RESULT_FILE_ORIGINAL ||
                    file.getFileCategory() == FileCategory.RESULT_ATTACHMENT) {
                filesToDelete.add(file);
            }
        }
        appFileRepository.deleteAll(filesToDelete);

        appService.sendAndStartProcess(appId);
    }

    private void deleteProcess(String processInstanceId) {
        try {
            runtimeService.deleteProcessInstance(processInstanceId, "");
        } catch (Exception e) {
            System.out.println("-------------------- runtimeServiceError");
            e.printStackTrace();
        }
        try {
            historyService.deleteHistoricProcessInstance(processInstanceId);
        } catch (Exception e) {
            System.out.println("-------------------- historyServiceError");
            e.printStackTrace();
        }
    }
}
