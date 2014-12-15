package com.hengyi.japp.dto;


import static org.apache.commons.beanutils.BeanUtils.setProperty;
import static org.apache.commons.lang3.StringUtils.deleteWhitespace;
import static org.apache.commons.lang3.StringUtils.split;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hengyi.japp.sap.annotation.SapConvertField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Embeddable
public class SmsSendResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(SmsSendResponseDTO.class);
    @SapConvertField("RESP")
    @Lob
    @Column(nullable = false)
    private String _resp;
    private String result;
    private String description;
    private String taskid;
    @Lob
    private String faillist;

    public SmsSendResponseDTO(String _resp) {
        super();
        this._resp = _resp;
        for (String pair : split(_resp, "&")) {
            String[] s = split(pair, "=");
            if (s.length < 2) {
                continue;
            }
            String key = s[0];
            String value = s[1];
            try {
                setProperty(this, key, value);
            } catch (Exception e) {
                log.error(_resp);
                log.error("短信发送的Response解析出错！key=" + key + ",value=" + value, e);
            }
        }
    }

    public boolean isSuccess() {
        return "0".equals(result);
    }

    public void setResult(String result) {
        this.result = deleteWhitespace(result);
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getResult() {
        return result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFaillist() {
        return faillist;
    }

    public void setFaillist(String faillist) {
        this.faillist = faillist;
    }

    public String get_resp() {
        return _resp;
    }

    public SmsSendResponseDTO() {
        super();
    }
}
