/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.sap.convert.impl;

import com.hengyi.japp.sap.convert.IFieldCopy;
import com.hengyi.japp.sap.convert.ISapConvert;
import com.sap.conn.jco.JCoMetaData;
import java.beans.PropertyDescriptor;
import javax.resource.NotSupportedException;

/**
 *
 * @author jzb
 */
public class FieldCopys {

    private Class<?> beanClass;
    private String beanPropertyName;
    private JCoMetaData metaData;
    private String sapFieldName;

    private PropertyDescriptor descriptor;

    static FieldCopys begin() {
        return new FieldCopys();
    }

    IFieldCopy build() throws Exception {
        if (beanClass == null) {
            throw new IllegalArgumentException("beanClass have no value");
        }
        if (metaData == null) {
            throw new IllegalArgumentException("JCoMetaData have no value");
        }
        if (beanPropertyName == null) {
            throw new IllegalArgumentException("beanPropertyName have no value");
        }
        if (sapFieldName == null) {
            throw new IllegalArgumentException("sapFieldName have no value");
        }
        return (!sapFieldName.contains("-")) ? buildDirect() : buildInDirect();
    }

    /**
     * 没有包含嵌套的结构或内表
     */
    private IFieldCopy buildDirect() throws Exception {
        if (!metaData.hasField(sapFieldName)) {
            return null;
        }

        Class<?> propertyType = descriptor.getPropertyType();
        if (Boolean.class.equals(propertyType)
                || boolean.class.equals(propertyType)) {
            return new FieldCopyBoolean(beanPropertyName, sapFieldName);
        }

        int sapFieldType = metaData.getType(sapFieldName);
        if (JCoMetaData.TYPE_STRUCTURE == sapFieldType) {
            ISapConvert sapConvert = SapConverts.begin()
                    .addRecordMetaData(metaData.getRecordMetaData(sapFieldName))
                    .addBeanClass(propertyType).build();
            return new FieldCopyStructure(beanPropertyName, sapFieldName, sapConvert);
        } else if (JCoMetaData.TYPE_TABLE == sapFieldType) {
            //TODO 内表支持
        } else {
            return new FieldCopyBase(beanPropertyName, sapFieldName);
        }
        return null;
    }

    //TODO 结构中的几个字段复制支持
    private IFieldCopy buildInDirect() throws Exception {
        throw new NotSupportedException();
    }

    FieldCopys addBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
        return this;
    }

    FieldCopys addBeanPropertyDescriptor(PropertyDescriptor descriptor) {
        this.descriptor = descriptor;
        this.beanPropertyName = descriptor.getName();
        return this;
    }

    FieldCopys addRecordMetaData(JCoMetaData metaData) {
        this.metaData = metaData;
        return this;
    }

    FieldCopys addSapFieldName(String sapFieldName) {
        this.sapFieldName = sapFieldName;
        return this;
    }

    private FieldCopys() {
    }
}
