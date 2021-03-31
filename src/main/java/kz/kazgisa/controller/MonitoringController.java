package kz.kazgisa.controller;

import kz.kazgisa.data.dto.AppArchDto;
import kz.kazgisa.service.MonitoringService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("monitoring")
public class MonitoringController {

    private final MonitoringService monitoringService;

    public MonitoringController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GetMapping("contracts")
    public ResponseEntity<?> getMonitoring(@RequestParam String by,
                                           Pageable pageable) {
        return ResponseEntity.ok(monitoringService.getContracts(by, pageable));
    }

    @PostMapping("zu")
    public ResponseEntity<?> getMonitoringZu(@RequestBody AppArchDto filter,
                                             Pageable pageable) {
        return ResponseEntity.ok(monitoringService.getMonitoringZu(filter, pageable));
    }

    @GetMapping("zu/{gid}/intersect")
    public ResponseEntity<?> getMonitoringZuIntersect(@PathVariable Long gid, Pageable pageable) {
        return ResponseEntity.ok(monitoringService.getMonitoringZuIntersect(gid, pageable));
    }
}
