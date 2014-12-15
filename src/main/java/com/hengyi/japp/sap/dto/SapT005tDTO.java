package com.hengyi.japp.sap.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SapT005tDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String land1;
	private String landx;

	public String getLand1() {
		return land1;
	}

	public void setLand1(String land1) {
		this.land1 = land1;
	}

	public String getLandx() {
		return landx;
	}

	public void setLandx(String landx) {
		this.landx = landx;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final SapT005tDTO other = (SapT005tDTO) o;
		return Objects.equal(getLand1(), other.getLand1());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getLand1());
	}
}
