package kz.kazgisa.service;

import kz.gov.pki.kalkan.jce.provider.KalkanProvider;
import kz.gov.pki.kalkan.xmldsig.KncaXS;
import kz.kazgisa.data.dto.SignResponse;
import org.apache.xml.security.Init;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.signature.XMLSignature;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.Provider;
import java.security.Security;
import java.security.cert.*;
import java.util.Date;

@Service
public class SignService {
    X509Certificate cert;

    public SignResponse validateSignature(String xmlSigned) {
        SignResponse response = new SignResponse();
        response.setSigned(true);
        response = verifySignature(xmlSigned, response);
        if (!response.getSigned()) {
            return response;
        }
//        response = checkCrl(response);
//        if (!response.getSigned()) {
//            return response;
//        }
        return response;
    }

//    public void initProvider() {
//        // Инициализация провайдера
//        Provider kalkanProvider = new KalkanProvider();
//
//
//        //Добавление провайдера в java.security.Security
//        boolean exists = false;
//        Provider[] providers = Security.getProviders();
//        for (Provider p : providers) {
//            if (p.getName().equals(kalkanProvider.getName())) {
//                exists = true;
//            }
//        }
//        if (!exists) {
//            Security.addProvider(kalkanProvider);
//        }
//        KncaXS.loadXMLSecurity();
//    }

    public SignResponse verifySignature(String xmlSigned, SignResponse response) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            Document doc = documentBuilder.parse(new ByteArrayInputStream(xmlSigned.getBytes("UTF-8")));

            Element sigElement = null;
            Element rootEl = (Element) doc.getFirstChild();

            Provider provider = new KalkanProvider();
            Security.addProvider(provider);
//          загружаем конфигурацию либо магической функцией
            KncaXS.loadXMLSecurity();

            NodeList list = rootEl.getElementsByTagName("ds:Signature");
            int length = list.getLength();
            for (int i = 0; i < length; i++) {
                Node sigNode = list.item(length - 1);
                sigElement = (Element) sigNode;
                if (sigElement == null) {
                    response.setSigned(false);
                    response.setMessage("Bad signature: Element 'ds:Reference' is not found in XML document");
                }
                Init.init();
//                initProvider();
                XMLSignature signature = new XMLSignature(sigElement, "");
                KeyInfo ki = signature.getKeyInfo();
                cert = null;
                cert = ki.getX509Certificate();
                response.setSubjectDn(cert.getSubjectDN().getName());
                response.setCertNotAfter(cert.getNotAfter());
                response.setCertNotBefore(cert.getNotBefore());
                if(cert.getNotAfter().before(new Date())) {
                    response.setSigned(false);
                    response.setMessage("Срок сертификата истек!");
                    return response;
                }
                String signedInfo = getSignedInfo(cert);
                response.setMessage(signedInfo);
                if (cert != null) {
                    Boolean result = signature.checkSignatureValue(cert);
                    response.setSigned(result);
                    if (!result) {
                        response.setMessage("Неверная подпись!");
                    }
                    if (!response.getSigned())
                        rootEl.removeChild(sigElement);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setSigned(false);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public SignResponse checkCrl(SignResponse response) {
//        X509CRL crl = downloadCRLFromWeb("/home/yerhat/data/rsa.crl");
        X509CRL crl = downloadCRLFromWeb("http://crl.pki.gov.kz/rsa.crl");
        Boolean revoked = crl.isRevoked(cert);
        if (!revoked) {
            crl = downloadCRLFromWeb("http://crl.pki.gov.kz/d_rsa.crl");
            revoked = crl.isRevoked(cert);
            if (!revoked) {
                response.setSigned(true);
                return response;
            }
        }
        response.setSigned(false);
        response.setMessage("Сертификат отозван");
        return response;
    }

    private X509CRL downloadCRLFromWeb(String crlURL) {
        try {
            URL url = new URL(crlURL);
            InputStream crlStream = url.openStream();
            try {
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                X509CRL crl = (X509CRL) cf.generateCRL(crlStream);
                return crl;
            } finally {
                crlStream.close();
            }
        } catch (IOException | CertificateException | CRLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getSignedInfo(X509Certificate cert) {
        String signedInfo = "";
        if (cert.getSubjectDN() != null) {
            String subjectDn = cert.getSubjectDN().getName();
            if (subjectDn.contains("OU=BIN")) {
                String companyName = "";
                try {
                    companyName = subjectDn.split(", O=")[1].split(", ST=")[0];
                } catch (Exception e) {
                }
                String bin = "";
                try {
                    bin = subjectDn.split("OU=BIN")[1].substring(0, 12);
                } catch (Exception e) {
                }
                signedInfo = companyName + ", БИН: " + bin;
            } else {
                String fio = "";
                try {
                    fio = subjectDn.split(", CN=")[1] + " ";
                    fio += subjectDn.split("GIVENNAME=")[1].split(", ST=")[0];
                } catch (Exception e) {
                }
                String iin = "";
                try {
                    iin = subjectDn.split("SERIALNUMBER=IIN")[1].substring(0, 12);
                } catch (Exception e) {
                }
                signedInfo = fio + ", ИИН: " + iin;
            }
        }
        return signedInfo;
    }

    public String getDsInfoProperty(String dsInfo, String prop) {
        String[] arr = dsInfo.split(",");
        String value = "";
        for (String element : arr) {
            if (element.contains(prop + "=")) {
                String[] strs = element.split("=");
                if (strs != null && strs.length > 1) {
                    value = strs[1];
                }
            }
        }
        return value;
    }
}
