package com.hengyi.japp.sap;

import com.hengyi.japp.dto.SmsSendDTO;
import com.hengyi.japp.dto.SmsSendResponseDTO;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

public interface SapService {

    JCoDestination getDestination() throws Exception;

    JCoFunction getFunction(String fName, JCoDestination dest) throws Exception;

    void execute(JCoFunction function, JCoDestination dest) throws Exception;

    String convertKunnr(String kunnr) throws Exception;

    String convertMatnr(String matnr) throws Exception;

    String convertLifnr(String lifnr) throws Exception;

    JCoTable findAllDomvalue(String I_DOMNAME) throws Exception;

    String findMobile(String id) throws Exception;

    SmsSendResponseDTO sendSms(SmsSendDTO smsSend) throws Exception;
}
