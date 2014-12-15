/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.sap.convert.impl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import com.sap.conn.jco.JCoRecord;

/**
 *
 * @author jzb
 */
public class FieldCopyBoolean extends FieldCopyBase {

    public static final Class<Boolean> BOOLEAN_CLASS = boolean.class;

    public FieldCopyBoolean(String beanPropertyName, String sapFieldName) {
        super(beanPropertyName, sapFieldName);
    }

    @Override
    public void setSapFieldValue(JCoRecord record, Object beanPropertyValue) throws Exception {
        if (beanPropertyValue == null) {
            super.setSapFieldValue(record, null);
        } else {
            super.setSapFieldValue(record, ((Boolean) beanPropertyValue) ? SAP_TRUE : SAP_FALSE);
        }
    }

    @Override
    public void setBeanPropertyValue(Object bean, Object sapFieldValue) throws Exception {
        super.setBeanPropertyValue(bean, SAP_TRUE.equals(sapFieldValue));
    }

    @Override
    protected void initBeanPropertyWriteMethod(Object bean, JCoRecord record) throws Exception {
        PropertyDescriptor descriptor = PropertyUtils.getPropertyDescriptor(bean, beanPropertyName);
        Method m = descriptor.getWriteMethod();
        if (m == null) {
            beanPropertyWriteMethod = null;
        } else {
            beanPropertyWriteMethod = MethodUtils.getMatchingAccessibleMethod(bean.getClass(), m.getName(), BOOLEAN_CLASS);
        }
    }
}
