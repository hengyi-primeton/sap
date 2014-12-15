package com.hengyi.japp.sap.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SapDd07vDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String domname;
	private String valpos;
	private String ddlanguage;
	private String domvalue_l;
	private String domvalue_h;
	private String ddtext;
	private String domval_ld;
	private String domval_hd;
	private String appval;

	public String getDomname() {
		return domname;
	}

	public void setDomname(String domname) {
		this.domname = domname;
	}

	public String getValpos() {
		return valpos;
	}

	public void setValpos(String valpos) {
		this.valpos = valpos;
	}

	public String getDdlanguage() {
		return ddlanguage;
	}

	public void setDdlanguage(String ddlanguage) {
		this.ddlanguage = ddlanguage;
	}

	public String getDomvalue_l() {
		return domvalue_l;
	}

	public void setDomvalue_l(String domvalue_l) {
		this.domvalue_l = domvalue_l;
	}

	public String getDomvalue_h() {
		return domvalue_h;
	}

	public void setDomvalue_h(String domvalue_h) {
		this.domvalue_h = domvalue_h;
	}

	public String getDdtext() {
		return ddtext;
	}

	public void setDdtext(String ddtext) {
		this.ddtext = ddtext;
	}

	public String getDomval_ld() {
		return domval_ld;
	}

	public void setDomval_ld(String domval_ld) {
		this.domval_ld = domval_ld;
	}

	public String getDomval_hd() {
		return domval_hd;
	}

	public void setDomval_hd(String domval_hd) {
		this.domval_hd = domval_hd;
	}

	public String getAppval() {
		return appval;
	}

	public void setAppval(String appval) {
		this.appval = appval;
	}

//	@Override
//	public boolean equals(final Object o) {
//		if (this == o) {
//			return true;
//		}
//		if (o == null || getClass() != o.getClass()) {
//			return false;
//		}
//		final SapDd07vDTO other = (SapDd07vDTO) o;
//		return Objects.equal(getDomname(), other.getDomname())
//				&& Objects.equal(getValpos(), other.getValpos());
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hashCode(getDomname(), getValpos());
//	}
}
