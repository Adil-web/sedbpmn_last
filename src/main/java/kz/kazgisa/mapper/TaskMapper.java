package kz.kazgisa.mapper;

import kz.kazgisa.data.dto.TaskDto;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.BeanUtils;

public class TaskMapper {
    public static TaskDto mapToDto(Task task) {
        TaskDto dto = new TaskDto();
        BeanUtils.copyProperties(task, dto);
        return dto;
    }

    public static TaskDto mapToDto(HistoricTaskInstance task) {
        TaskDto dto = new TaskDto();
        BeanUtils.copyProperties(task, dto);
        return dto;
    }
}
