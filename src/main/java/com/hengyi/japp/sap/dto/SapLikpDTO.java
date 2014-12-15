package com.hengyi.japp.sap.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SapLikpDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String vbeln;

    public String getVbeln() {
        return vbeln;
    }

    public void setVbeln(String vbeln) {
        this.vbeln = vbeln;
    }

    public SapLikpDTO() {
        super();
    }

    public SapLikpDTO(String vbeln) {
        super();
        this.vbeln = vbeln;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SapLikpDTO other = (SapLikpDTO) o;
        return Objects.equal(getVbeln(), other.getVbeln());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getVbeln());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).addValue(getVbeln()).toString();
    }
}
