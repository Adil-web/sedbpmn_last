package kz.kazgisa.data.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class XmlData {
    private String data;

    private String signedData;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSignedData() {
        return signedData;
    }

    public void setSignedData(String signedData) {
        this.signedData = signedData;
    }
}
