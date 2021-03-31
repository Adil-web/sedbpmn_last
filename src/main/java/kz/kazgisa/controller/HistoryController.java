package kz.kazgisa.controller;

import kz.kazgisa.data.dto.SearchCriteriaDto;
import kz.kazgisa.service.HistoryCustomService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Controller
@RequestMapping("history")
public class HistoryController {
    private final HistoryCustomService historyCustomService;

    public HistoryController(HistoryCustomService historyCustomService) {
        this.historyCustomService = historyCustomService;
    }

    @GetMapping("tasks")
    public ResponseEntity<?> getTasks(@RequestParam(required = false) String assignee,
                                      @RequestParam(required = false, defaultValue = "true") Boolean finished,
                                      Pageable pageable) {
        return ResponseEntity.ok(historyCustomService.getUserTasks(assignee,finished,pageable));
    }

    @PostMapping("tasks/filter")
    public ResponseEntity<?> filterTasks(@RequestBody List<SearchCriteriaDto> list,
                                         Pageable pageable) {
        return ResponseEntity.ok(historyCustomService.filterUserTasks(list, pageable));
    }

    @GetMapping("tasks/{id}")
    public ResponseEntity<?> getAppHistoryById(@PathVariable String id) {
        return ResponseEntity.ok(historyCustomService.getTaskById(id));
    }

    @GetMapping("tasks/{id}/variables")
    public ResponseEntity<?> getAppHistoryVariablesById(@PathVariable String id) {
        return ResponseEntity.ok(historyCustomService.getHistoryTaskVariables(id));
    }

    @GetMapping("userapp/{id}")
    public ResponseEntity<?> getAppHistory(@PathVariable Long id,@RequestParam(required = false) Long subserviceId) {
        return ResponseEntity.ok(historyCustomService.getAppHistory(id,subserviceId));
    }


    @GetMapping("tasks/{id}/form")
    public ResponseEntity<?> getTasks(@PathVariable String id) {
        return ResponseEntity.ok(historyCustomService.getUserTasksForm(id));
    }

    @GetMapping("executor-stats")
    public ResponseEntity<?> getExecStats(@RequestParam (required = false) Long from,
                                          @RequestParam (required = false) Long to) {
        if(from == null) {
            from = LocalDate.of(2019,1,1).atStartOfDay(ZoneId.systemDefault())
                    .toInstant().toEpochMilli();
        }
        if(to == null) {
            to = LocalDate.of(2025,1,1).atStartOfDay(ZoneId.systemDefault())
                    .toInstant().toEpochMilli();
        }
        return ResponseEntity.ok(historyCustomService.getTaskStats(from, to));
    }
}
