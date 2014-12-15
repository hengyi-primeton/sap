package com.hengyi.japp.sap.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SapTvkttDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String ktgrd;

	private String vtext;

	public String getKtgrd() {
		return ktgrd;
	}

	public void setKtgrd(String ktgrd) {
		this.ktgrd = ktgrd;
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
		final SapTvkttDTO other = (SapTvkttDTO) o;
		return Objects.equal(getKtgrd(), other.getKtgrd());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getKtgrd());
	}
}
