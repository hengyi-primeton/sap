package com.hengyi.japp.sap.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SapSkatDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String ktopl;
	private String saknr;
	private String txt20;

	public String getKtopl() {
		return ktopl;
	}

	public void setKtopl(String ktopl) {
		this.ktopl = ktopl;
	}

	public String getSaknr() {
		return saknr;
	}

	public void setSaknr(String saknr) {
		this.saknr = saknr;
	}

	public String getTxt20() {
		return txt20;
	}

	public void setTxt20(String txt20) {
		this.txt20 = txt20;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final SapSkatDTO other = (SapSkatDTO) o;
		return Objects.equal(getKtopl(), other.getKtopl())
				&& Objects.equal(getSaknr(), other.getSaknr());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getKtopl(), getSaknr());
	}
}
