package com.hengyi.japp.sap.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SapTcurtDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String waers;

	private String ltext;
	private String ktext;

	public String getWaers() {
		return waers;
	}

	public void setWaers(String waers) {
		this.waers = waers;
	}

	public String getLtext() {
		return ltext;
	}

	public void setLtext(String ltext) {
		this.ltext = ltext;
	}

	public String getKtext() {
		return ktext;
	}

	public void setKtext(String ktext) {
		this.ktext = ktext;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final SapTcurtDTO other = (SapTcurtDTO) o;
		return Objects.equal(getWaers(), other.getWaers());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getWaers());
	}
}
