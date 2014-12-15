package com.hengyi.japp.sap.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SapTspatDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String spart;
    private String vtext;

	public String getSpart() {
		return spart;
	}

	public void setSpart(String spart) {
		this.spart = spart;
	}

	public String getVtext() {
		return vtext;
	}

	public void setVtext(String vtext) {
		this.vtext = vtext;
	}

	@Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SapTspatDTO other = (SapTspatDTO) o;
        return Objects.equal(getSpart(), other.getSpart());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getSpart());
    }
}
