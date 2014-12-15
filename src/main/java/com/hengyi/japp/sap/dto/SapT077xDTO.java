package com.hengyi.japp.sap.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SapT077xDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String ktokd;
	private String txt30;

	public String getKtokd() {
		return ktokd;
	}

	public void setKtokd(String ktokd) {
		this.ktokd = ktokd;
	}

	public String getTxt30() {
		return txt30;
	}

	public void setTxt30(String txt30) {
		this.txt30 = txt30;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final SapT077xDTO other = (SapT077xDTO) o;
		return Objects.equal(getKtokd(), other.getKtokd());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getKtokd());
	}
}
