package com.hengyi.japp.sap.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SapTskdtDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String tatyp;
	private String taxkd;

	private String vtext;

	public String getTatyp() {
		return tatyp;
	}

	public void setTatyp(String tatyp) {
		this.tatyp = tatyp;
	}

	public String getTaxkd() {
		return taxkd;
	}

	public void setTaxkd(String taxkd) {
		this.taxkd = taxkd;
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
		final SapTskdtDTO other = (SapTskdtDTO) o;
		return Objects.equal(getTatyp(), other.getTatyp())
				&& Objects.equal(getTaxkd(), other.getTaxkd());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getTatyp(), getTaxkd());
	}
}
