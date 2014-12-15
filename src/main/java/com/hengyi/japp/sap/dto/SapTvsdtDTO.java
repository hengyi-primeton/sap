package com.hengyi.japp.sap.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SapTvsdtDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String stgku;

	private String bezei20;

	public String getStgku() {
		return stgku;
	}

	public void setStgku(String stgku) {
		this.stgku = stgku;
	}

	public String getBezei20() {
		return bezei20;
	}

	public void setBezei20(String bezei20) {
		this.bezei20 = bezei20;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final SapTvsdtDTO other = (SapTvsdtDTO) o;
		return Objects.equal(getStgku(), other.getStgku());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getStgku());
	}
}
