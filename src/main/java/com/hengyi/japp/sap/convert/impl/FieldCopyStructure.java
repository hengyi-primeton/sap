/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.sap.convert.impl;

import com.hengyi.japp.sap.convert.ISapConvert;
import com.sap.conn.jco.JCoRecord;
import com.sap.conn.jco.JCoStructure;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author jzb
 */
public class FieldCopyStructure extends FieldCopyBase {

    /**
     *
     */
    private final ISapConvert sapConvert;
    private Class<?> beanPropertyType;

    public FieldCopyStructure(String beanPropertyName, String sapFieldName, ISapConvert sapConvert) {
        super(beanPropertyName, sapFieldName);
        this.sapConvert = sapConvert;
    }

    @Override
    public void setBeanPropertyValue(Object bean, Object sapFieldValue) throws Exception {
        JCoStructure structure = (JCoStructure) sapFieldValue;
        Object value = sapConvert.convert(beanPropertyType, structure);
        super.setBeanPropertyValue(bean, value);
    }

    @Override
    public void setSapFieldValue(JCoRecord record, Object value) throws Exception {
        JCoStructure structure = record.getStructure(sapFieldName);
        sapConvert.copy(structure, value);
        super.setSapFieldValue(record, structure);
    }

    protected void initOther(Object bean, JCoRecord record) throws Exception {
        beanPropertyType = PropertyUtils.getPropertyType(bean, beanPropertyName);
    }

}
