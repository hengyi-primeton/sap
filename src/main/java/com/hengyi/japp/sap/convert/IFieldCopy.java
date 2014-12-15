package com.hengyi.japp.sap.convert;

import com.sap.conn.jco.JCoRecord;

public interface IFieldCopy extends IValueCopy {

    String getBeanPropertyName();

    Object getBeanPropertyValue(Object bean) throws Exception;

    void setBeanPropertyValue(Object bean, Object sapFieldValue) throws Exception;

    String getSapFieldName();

    Object getSapFieldValue(JCoRecord record) throws Exception;

    void setSapFieldValue(JCoRecord record, Object beanPropertyValue) throws Exception;
}
