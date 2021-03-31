package kz.kazgisa.controller;

import kz.kazgisa.service.AnalyticsService;
import kz.kazgisa.utils.CustomDateUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

@Controller
@RequestMapping("analytics")
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("menu")
    public ResponseEntity<?> getFiles(@RequestParam(required = false) String username, Principal principal) {
        String user;
        if(username != null)
            user= username;
        else
            user = principal.getName();
        return ResponseEntity.ok(analyticsService.getMenuAnalytics(user));
    }

    @GetMapping("stats")
    public ResponseEntity<?> getStats(@RequestParam(required = false) Date startDate,
                                      @RequestParam(required = false) Date endDate,
                                      @RequestParam(required = false) Long subserviceId,
                                      Principal principal) {
        return ResponseEntity.ok(analyticsService.getStatAnalytics(subserviceId,startDate,endDate));
    }

}
