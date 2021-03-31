package kz.kazgisa.service;

import kz.kazgisa.data.entity.Correspondence;
import kz.kazgisa.data.entity.CorrespondenceFile;
import kz.kazgisa.data.entity.dict.CorrespondenceCategory;
import kz.kazgisa.data.enums.CorrespondenceType;
import kz.kazgisa.data.repositories.CorrespondenceCategoryRepository;
import kz.kazgisa.data.repositories.CorrespondenceFileRepository;
import kz.kazgisa.data.repositories.CorrespondenceRepository;
import org.apache.commons.lang3.BooleanUtils;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CorrespondenceService {
    private final CorrespondenceRepository correspondenceRepository;
    private final CorrespondenceCategoryRepository correspondenceCategoryRepository;
    private final CorrespondenceFileRepository correspondenceFileRepository;
    private final TaskService taskService;

    public CorrespondenceService(CorrespondenceRepository correspondenceRepository, CorrespondenceCategoryRepository correspondenceCategoryRepository, CorrespondenceFileRepository correspondenceFileRepository, TaskService taskService) {
        this.correspondenceRepository = correspondenceRepository;
        this.correspondenceCategoryRepository = correspondenceCategoryRepository;
        this.correspondenceFileRepository = correspondenceFileRepository;
        this.taskService = taskService;
    }

    public Page<Correspondence> getAll(CorrespondenceType type, Pageable pageable) {
        return correspondenceRepository.findByType(type,pageable);
    }


    @Transactional
    public Correspondence save(Correspondence correspondence) {
        correspondence = correspondenceRepository.save(correspondence);
        if(BooleanUtils.isTrue(correspondence.isExecutable())) {
            Task task=taskService.createTaskQuery().taskVariableValueEquals("correspondenceId",correspondence.getId()).singleResult();
            if(task ==null)
                task = taskService.newTask();
            task.setAssignee(correspondence.getExecutor().getUsername());
            task.setDueDate(correspondence.getExecuteDueDate());
            task.setName("Корресподенция для исполнения");
            taskService.saveTask(task);
            taskService.setVariable(task.getId(),"correspondenceId", correspondence.getId());
            taskService.setVariable(task.getId(),"type", "TASK");
        }
        return correspondence;
    }

    public List<CorrespondenceCategory> getCategories() {
        return correspondenceCategoryRepository.findAll();
    }

    @Transactional
    public void deleteFile(Long id) {
        correspondenceFileRepository.deleteById(id);
    }

    public CorrespondenceFile getFileByName(String id) {
        return correspondenceFileRepository.findFirstByName(id);
    }

    public List addFile(List<CorrespondenceFile> files, Long appId) {
        for (CorrespondenceFile file : files) {
            file.setCorrespondence(correspondenceRepository.findById(appId).get());
        }

        return correspondenceFileRepository.saveAll(files);
    }


    public List<CorrespondenceFile> getFiles(Long id) {
        return correspondenceFileRepository.findByCorrespondenceId(id);
    }

    public Correspondence getById(Long id) {
        return correspondenceRepository.findById(id).get();
    }

    @Transactional
    public Correspondence deleteById(Long id) {
        correspondenceRepository.deleteById(id);
        return new Correspondence();
    }
}
