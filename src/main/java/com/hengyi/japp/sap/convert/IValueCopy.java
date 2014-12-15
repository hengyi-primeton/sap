/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.sap.convert;

import com.sap.conn.jco.JCoRecord;

/**
 *
 * @author jzb
 */
public interface IValueCopy {

    /**
     * 复制FieldName和属性名一样的值,record-->bean
     *
     * 如果是@JCoTable,那么复制当前行的值
     *
     * @param bean Java端的Bean
     * @param record Sap端的 @JCoRecord
     * @throws java.lang.Exception
     */
    void copy(Object bean, JCoRecord record) throws Exception;

    /**
     * 复制FieldName和属性名一样的值,bean-->record
     *
     * 如果是@JCoTable,那么复制当前行的值
     *
     * @param record Sap端的 @JCoRecord
     * @param bean Java端的Bean
     * @throws java.lang.Exception
     */
    void copy(JCoRecord record, Object bean) throws Exception;

}
