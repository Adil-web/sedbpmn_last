package kz.kazgisa.controller;

import kz.kazgisa.data.entity.AppOrgConnection;
import kz.kazgisa.service.AppService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("apporg")
public class AppOrgController {

    private final AppService appService;

    public AppOrgController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping
    public ResponseEntity<?> getApp(@RequestParam Long organizationId,
                                    @RequestParam Long appId) {
        return ResponseEntity.ok(appService.getAppOrgByAppIdAndOrgId(appId, organizationId));
    }

    @GetMapping("/{appOrgId}/connections")
    public ResponseEntity<?> getConnections(@PathVariable Long appOrgId) {
        return ResponseEntity.ok(appService.getConnections(appOrgId));
    }

    @PostMapping("/{appOrgId}/connections")
    public ResponseEntity<?> addConnections(@PathVariable Long appOrgId,
                                            @RequestBody List<AppOrgConnection> connections) {
        appService.addConnection(appOrgId, connections);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{appOrgId}/connections/{connectionId}")
    public ResponseEntity<?> deleteConnection(@PathVariable Long appOrgId,
                                              @PathVariable Long connectionId) {
        appService.deleteConnection(connectionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
