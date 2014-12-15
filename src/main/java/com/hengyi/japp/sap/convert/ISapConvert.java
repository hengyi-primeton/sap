package com.hengyi.japp.sap.convert;

import java.util.List;

import com.sap.conn.jco.JCoRecord;
import com.sap.conn.jco.JCoTable;

public interface ISapConvert extends IValueCopy {

    /**
     * @param <T>
     * @param clazz Java端的Bean
     * @param record Sap端的 @JCoRecord
     * @return
     * @throws java.lang.Exception
     */
    <T> T convert(Class<T> clazz, JCoRecord record) throws Exception;

    /**
     * @param <T>
     * @param clazz Java端的Bean
     * @param table Sap端的 @JCoTable
     * @return
     * @throws java.lang.Exception
     */
    <T> List<T> convert(Class<T> clazz, JCoTable table) throws Exception;
}
