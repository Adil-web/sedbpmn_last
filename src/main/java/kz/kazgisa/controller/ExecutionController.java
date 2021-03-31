package kz.kazgisa.controller;

import kz.kazgisa.service.ProcessCustomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

@Controller
@RequestMapping("executions")
public class ExecutionController {
    private final ProcessCustomService processCustomService;

    public ExecutionController(ProcessCustomService processCustomService) {
        this.processCustomService = processCustomService;
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateProcess(@PathVariable String id, @RequestBody Map<String,Object> variables) throws ParseException {
        return ResponseEntity.ok(processCustomService.updateProcess(id, variables));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProcess(@PathVariable String id,
                                           @RequestParam(required = false) Long appId) throws ParseException {
        processCustomService.deleteProcessApp(id, appId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("{id}/restart")
    public ResponseEntity<?> restartProcess(@PathVariable String id,
                                            @RequestParam Long appId) {
        processCustomService.restartProcessApp(id, appId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
