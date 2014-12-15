package com.hengyi.japp.sap.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SapTpritDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String lprio;

	private String bezei;

	public String getLprio() {
		return lprio;
	}

	public void setLprio(String lprio) {
		this.lprio = lprio;
	}

	public String getBezei() {
		return bezei;
	}

	public void setBezei(String bezei) {
		this.bezei = bezei;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final SapTpritDTO other = (SapTpritDTO) o;
		return Objects.equal(getLprio(), other.getLprio());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getLprio());
	}
}
