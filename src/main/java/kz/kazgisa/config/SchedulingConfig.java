package kz.kazgisa.config;

import kz.kazgisa.service.AppService;
import kz.kazgisa.service.ResultSendEqyzmetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@EnableScheduling
public class SchedulingConfig {

    @Autowired
    ResultSendEqyzmetService resultSendEqyzmetService;

    @Scheduled(fixedRate = 600000)
    public void sendUnsentEskizFiles() {
        resultSendEqyzmetService.sendUnsentEskizFiles();
    }
}
