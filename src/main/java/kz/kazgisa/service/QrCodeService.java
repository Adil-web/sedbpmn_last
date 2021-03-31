package kz.kazgisa.service;

import kz.kazgisa.data.entity.App;
import kz.kazgisa.data.repositories.AppRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class QrCodeService implements JavaDelegate {
    private final AppRepository appRepository;

    public QrCodeService(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        long id = ((Number) delegateExecution.getVariable("appId")).longValue();
        App app = appRepository.findById(id).get();
        if(app.getSubservice().getId() !=21L)
            app.setFactEndDate(new Date());
        app.setArchSigned(true);
        appRepository.save(app);        //add qr code
    }
}
