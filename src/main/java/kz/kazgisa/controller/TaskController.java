package kz.kazgisa.controller;

import kz.kazgisa.data.dto.SearchCriteriaDto;
import kz.kazgisa.data.dto.TaskDto;
import kz.kazgisa.service.TaskCustomService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("tasks")
public class TaskController {

    private final TaskCustomService taskCustomService;

    public TaskController(TaskCustomService taskCustomService) {
        this.taskCustomService = taskCustomService;
    }

    @GetMapping
    public ResponseEntity<?> getTasks(@RequestParam(required = false) String assignee,
                                      @RequestParam(required = false) String role,
                                      @RequestParam(required = false) String owner,
                                      Pageable pageable) {
        return ResponseEntity.ok(taskCustomService.getUserTasks(assignee, role, owner, pageable));
    }

    @PostMapping("filter")
    public ResponseEntity<?> filterTasks(@RequestBody List<SearchCriteriaDto> list,
                                      @RequestParam(required = false) String sort,
                                      Pageable pageable) {
        return ResponseEntity.ok(taskCustomService.filterUserTasks(list, pageable, sort));
    }



    @GetMapping("{id}")
    public ResponseEntity<?> getTasksById(@PathVariable String id) {
        return ResponseEntity.ok(taskCustomService.getUserTaskById(id));
    }

    @GetMapping("{id}/form")
    public ResponseEntity<?> getForm(@PathVariable String id) {
        return ResponseEntity.ok(taskCustomService.getTaskVariables(id));
    }

    @PostMapping("{id}/complete")
    public ResponseEntity<?> endUserTask(@PathVariable String id,
                                         @RequestBody Map<String, Object> map,
                                         @RequestParam(required = false) String assignee) throws ParseException {
        return ResponseEntity.ok(taskCustomService.completeTask(id, map, assignee));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateTask(@PathVariable String id, @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskCustomService.updateTask(id, taskDto));
    }

    @PutMapping("assignees")
    public ResponseEntity<?> setAssignees(@RequestBody Map<String, String> body) {
        String currentAssignee = body.get("currentAssignee");
        String newAssignee = body.get("newAssignee");
        if(currentAssignee != null && newAssignee != null) {
            taskCustomService.setAssignees(currentAssignee, newAssignee);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("{id}/next/assignees")
    public ResponseEntity<?> getNextAssignees(@PathVariable String id,
                                              @RequestBody Map<String, Object> map) {
        return ResponseEntity.ok(taskCustomService.getNextAssignees(id, map));
    }
}
