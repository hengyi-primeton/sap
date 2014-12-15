package com.hengyi.japp.sap.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SapT188tDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String konda;

	private String vtext;

	public String getKonda() {
		return konda;
	}

	public void setKonda(String konda) {
		this.konda = konda;
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
		final SapT188tDTO other = (SapT188tDTO) o;
		return Objects.equal(getKonda(), other.getKonda());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getKonda());
	}
}
