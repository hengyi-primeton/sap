package com.hengyi.japp.sap.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SapT151tDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String kdgrp;

	private String ktext;

	public String getKdgrp() {
		return kdgrp;
	}

	public void setKdgrp(String kdgrp) {
		this.kdgrp = kdgrp;
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
		final SapT151tDTO other = (SapT151tDTO) o;
		return Objects.equal(getKdgrp(), other.getKdgrp());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getKdgrp());
	}
}
