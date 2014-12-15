/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.sap.convert.impl;

import com.hengyi.japp.sap.convert.IFieldCopy;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import javax.validation.constraints.NotNull;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.hibernate.validator.constraints.NotEmpty;

import com.sap.conn.jco.JCoRecord;

/**
 *
 * @author jzb
 */
public class FieldCopyBase implements IFieldCopy {

    public static final String SAP_TRUE = "X";
    public static final String SAP_FALSE = "";

    protected final String beanPropertyName;
    protected final String sapFieldName;

    public FieldCopyBase(@NotNull @NotEmpty String beanPropertyName, @NotNull @NotEmpty String sapFieldName) {
        this.beanPropertyName = beanPropertyName;
        this.sapFieldName = sapFieldName;
    }

    //@Override
    public String getBeanPropertyName() {
        return beanPropertyName;
    }

    //@Override
    public String getSapFieldName() {
        return sapFieldName;
    }

    //@Override
    public void copy(Object bean, JCoRecord record) throws Exception {
        ensureMethods(bean, record);
        if (beanPropertyWriteMethod == null) {
            return;
        }
        Object sapFieldValue = getSapFieldValue(record);
        setBeanPropertyValue(bean, sapFieldValue);
    }

//    @Override
    public Object getSapFieldValue(JCoRecord record) {
        return record.getValue(sapFieldName);
    }

//    @Override
    public void setBeanPropertyValue(Object bean, Object sapFieldValue) throws Exception {
        beanPropertyWriteMethod.invoke(bean, sapFieldValue);
    }

//    @Override
    public void copy(JCoRecord record, Object bean) throws Exception {
        ensureMethods(bean, record);
        if (beanPropertyReadMethod == null) {
            return;
        }
        Object beanPropertyValue = getBeanPropertyValue(bean);
        setSapFieldValue(record, beanPropertyValue);
    }

//    @Override
    public Object getBeanPropertyValue(Object bean) throws Exception {
        return beanPropertyReadMethod.invoke(bean, new Object[0]);
    }

//    @Override
    public void setSapFieldValue(JCoRecord record, Object beanPropertyValue) throws Exception {
        record.setValue(sapFieldName, beanPropertyValue);
    }

//    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + beanPropertyName + "<->" + sapFieldName + "]";
    }

    private boolean isEnsure = false;
    protected Method beanPropertyReadMethod;
    protected Method beanPropertyWriteMethod;
    protected Method sapFieldReadMethod;
    protected Method sapFieldWriteMethod;

    private void ensureMethods(Object bean, JCoRecord record) throws Exception {
        if (isEnsure) {
            return;
        }
        synchronized (this) {
            if (isEnsure) {
                return;
            }

            initSapFieldReadMethod(bean, record);
            initSapFieldWriteMethod(bean, record);
            initBeanPropertyReadMethod(bean, record);
            initBeanPropertyWriteMethod(bean, record);
            initOhter();

            isEnsure = true;
        }
    }

    protected void initOhter() {
    }

    protected Method getBeanPropertyReadMethod() {
        return beanPropertyReadMethod;
    }

    protected Method getBeanPropertyWriteMethod() {
        return beanPropertyWriteMethod;
    }

    protected Method getSapFieldReadMethod() {
        return sapFieldReadMethod;
    }

    protected Method getSapFieldWriteMethod() {
        return sapFieldWriteMethod;
    }

    protected void initSapFieldReadMethod(Object bean, JCoRecord record) throws Exception {
        sapFieldReadMethod = JCoRecord.class.getMethod("getValue", String.class);
    }

    protected void initSapFieldWriteMethod(Object bean, JCoRecord record) throws Exception {
        sapFieldWriteMethod = JCoRecord.class.getMethod("setValue", String.class, Object.class);
    }

    protected void initBeanPropertyReadMethod(Object bean, JCoRecord record) throws Exception {
        PropertyDescriptor descriptor = PropertyUtils.getPropertyDescriptor(bean, beanPropertyName);
        beanPropertyReadMethod = descriptor.getReadMethod();
    }

    protected void initBeanPropertyWriteMethod(Object bean, JCoRecord record) throws Exception {
        PropertyDescriptor descriptor = PropertyUtils.getPropertyDescriptor(bean, beanPropertyName);
        beanPropertyWriteMethod = descriptor.getWriteMethod();
        if (beanPropertyWriteMethod == null) {
            return;
        }
        String methodName = beanPropertyWriteMethod.getName();
        String expectedTypeString = record.getMetaData().getClassNameOfField(sapFieldName);
        Class<?> sapFieldType = Class.forName(expectedTypeString);
        beanPropertyWriteMethod = MethodUtils.getMatchingAccessibleMethod(bean.getClass(), methodName, sapFieldType);
    }
}
