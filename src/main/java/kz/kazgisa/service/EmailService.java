package kz.kazgisa.service;

import kz.kazgisa.sendpulse.Sendpulse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {
    private Sendpulse sendpulse;
    private String portalEmail = "no-reply@eatyrau.kz";
    private String portalName = "Портал услуг eatyrau";

    public EmailService() {
        sendpulse = new Sendpulse("8726ae27cc8d7f08eea5aa70902a6f2b", "4360d8f9c569bcaf7686d527e1030f90");
    }

    public void sendEmail(String toName, String toEmail, String subject, String htmlBody) {
        sendEmail(this.portalName, this.portalEmail, toName, toEmail, subject, htmlBody);
    }

    public void sendEmail(String fromName, String fromEmail, String toName, String toEmail, String subject, String htmlBody) {
        Map<String, Object> from = new HashMap<String, Object>();
        from.put("name", fromName);
        from.put("email", fromEmail);
        ArrayList<Map> to = new ArrayList<Map>();
        Map<String, Object> elementto = new HashMap<String, Object>();
        elementto.put("name", toName);
        elementto.put("email", toEmail);
        to.add(elementto);
        Map<String, Object> emaildata = new HashMap<String, Object>();
        emaildata.put("html", htmlBody);
//        emaildata.put("text","");
        emaildata.put("subject", subject);
        emaildata.put("from",from);
        emaildata.put("to",to);
//        if(attachments.size()>0){
//            emaildata.put("attachments_binary",attachments);
//        }
        Map<String, Object> result = sendpulse.smtpSendMail(emaildata);
        System.out.println("Results: " + result);
    }
}
