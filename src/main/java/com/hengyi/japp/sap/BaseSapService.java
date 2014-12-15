package com.hengyi.japp.sap;

import static com.hengyi.japp.sap.Constant.ZCONVERT_KUNNR;
import static com.hengyi.japp.sap.Constant.ZCONVERT_LIFNR;
import static com.hengyi.japp.sap.Constant.ZCONVERT_MATNR;
import static com.hengyi.japp.sap.Constant.ZFINDALL_DD_DOMVALUE;
import static com.hengyi.japp.sap.Constant.ZFIND_MOBILE;
import static com.hengyi.japp.sap.Constant.ZSEND_SMS;
import static com.hengyi.japp.sap.SapUtil.convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hengyi.japp.dto.SmsSendDTO;
import com.hengyi.japp.dto.SmsSendResponseDTO;
import com.hengyi.japp.sap.annotation.SapFunctionHandler;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.Environment;
import com.sap.conn.jco.server.DefaultServerHandlerFactory.FunctionHandlerFactory;
import com.sap.conn.jco.server.JCoServer;
import com.sap.conn.jco.server.JCoServerFactory;
import com.sap.conn.jco.server.JCoServerFunctionHandler;
import com.sap.conn.jco.server.JCoServerState;

public abstract class BaseSapService implements SapService {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    static {
        if (!Environment.isDestinationDataProviderRegistered()) {
            MyDataProvider dataProvider = new MyDataProvider();

            System.out.println("========注册SAP DESTINATION========");
            Environment.registerDestinationDataProvider(dataProvider);

            System.out.println("========注册SAP SERVER========");
            Environment.registerServerDataProvider(dataProvider);
        }
    }

    protected abstract DestinationType getDestinationType();

    private JCoDestination getDestination(DestinationType type) throws Exception {
        try {
            return JCoDestinationManager.getDestination(type.name());
        } catch (JCoException e) {
            log.error("获取SAP DESTINATION出错", e);
            throw new Exception("ERROR.SAP", e);
        }
    }

    public JCoDestination getDestination() throws Exception {
        return getDestination(getDestinationType());
    }

    public JCoFunction getFunction(String fName, JCoDestination dest) throws Exception {
        try {
            return dest.getRepository().getFunction(fName);
        } catch (JCoException e) {
            log.error("获取SAP FUNCTION出错：{}", fName);
            log.error("", e);
            throw new Exception("ERROR.SAP", e);
        }
    }

    public void execute(JCoFunction function, JCoDestination dest) throws Exception {
        log.debug("execute sap function：{}", function.getName());
        try {
            function.execute(dest);

            Exception[] exceptions = function.getExceptionList();
            if (exceptions != null) {
                log.error("functionName:{}", function.getName());
                log.error("importParameterList:{}", function.getImportParameterList());
                log.error("changingParameterList:{}", function.getChangingParameterList());
                log.error("tableParameterList:{}", function.getTableParameterList());
                log.error("exceptions:");
                for (Exception e : exceptions) {
                    log.error("", e);
                }
                throw new Exception();
            }
        } catch (JCoException e) {
            log.error("sap function {} execute error!", function.getName());
            throw new Exception("ERROR.SAP.FUNCTION.EXECUTE:" + function.getName(), e);
        }
    }

    public SmsSendResponseDTO sendSms(SmsSendDTO smsSend) throws Exception {
        JCoDestination dest = getDestination(DestinationType.EQ);
        JCoFunction f = getFunction(ZSEND_SMS, dest);
        JCoStructure s = f.getImportParameterList().getStructure("I_SMS_SEND");
        s.setValue("CONTENT", smsSend.getContent());
        s.setValue("SERIAL_NUMBER", smsSend.getSerialNumber());
        s.setValue("SEND_DATE", smsSend.getSendDate());
        s.setValue("SEND_CHECK_TYPE", smsSend.getSendCheckType());
        JCoTable phones = s.getTable("PHONES");
        for (String phone : smsSend.getPhones()) {
            phones.appendRow();
            phones.setValue(0, phone);
        }
        s.setValue("PHONES", phones);
        execute(f, dest);
        return convert(SmsSendResponseDTO.class, f.getExportParameterList().getStructure("E_SMS_SEND_RESPONSE"));
    }

    public String convertKunnr(String kunnr) throws Exception {
        JCoDestination dest = getDestination(DestinationType.EQ);
        JCoFunction f = getFunction(ZCONVERT_KUNNR, dest);
        f.getImportParameterList().setValue("I_KUNNR", kunnr);
        execute(f, dest);
        return f.getExportParameterList().getString("E_KUNNR");
    }

    public String convertMatnr(String matnr) throws Exception {
        JCoDestination dest = getDestination(DestinationType.EQ);
        JCoFunction f = getFunction(ZCONVERT_MATNR, dest);
        f.getImportParameterList().setValue("I_MATNR", matnr);
        execute(f, dest);
        return f.getExportParameterList().getString("E_MATNR");
    }

    public String convertLifnr(String lifnr) throws Exception {
        JCoDestination dest = getDestination(DestinationType.EQ);
        JCoFunction f = getFunction(ZCONVERT_LIFNR, dest);
        f.getImportParameterList().setValue("I_LIFNR", lifnr);
        execute(f, dest);
        return f.getExportParameterList().getString("E_LIFNR");
    }

    public JCoTable findAllDomvalue(String I_DOMNAME) throws Exception {
        JCoDestination dest = getDestination(DestinationType.PRO);
        JCoFunction f = getFunction(ZFINDALL_DD_DOMVALUE, dest);
        f.getImportParameterList().setValue("I_DOMNAME", I_DOMNAME);
        execute(f, dest);
        return f.getTableParameterList().getTable("ET_DD07V");
    }

    public String findMobile(String id) throws Exception {
        JCoDestination dest = getDestination(DestinationType.EQ);
        JCoFunction f = getFunction(ZFIND_MOBILE, dest);
        f.getImportParameterList().setValue("I_ID", id);
        execute(f, dest);
        return f.getExportParameterList().getString("E_MOBILE");
    }

    private JCoServer getServer(DestinationType type) throws Exception {
        try {
            return JCoServerFactory.getServer(type.name());
        } catch (JCoException e) {
            log.error("获取SAP SERVER出错", e);
            throw new Exception("ERROR.SAP", e);
        }
    }

    public final JCoServer getServer() throws Exception {
        return getServer(getDestinationType());
    }

    protected void registerFunctionHandler(JCoServerFunctionHandler handler, JCoServer server) throws Exception {
        String fName = getFunctionName(handler);
        if (fName == null) {
            return;
        }
        FunctionHandlerFactory factory = (FunctionHandlerFactory) server.getCallHandlerFactory();
        if (factory == null) {
            factory = new FunctionHandlerFactory();
            server.setCallHandlerFactory(factory);
        }
        factory.registerHandler(fName, handler);
        startJcoServer(server);
        log.info("注册Sap Function：{},Class:{}", fName, handler.getClass().getName());
    }

    protected void removeFunctionHandler(JCoServerFunctionHandler handler, JCoServer server) {
        String fName = getFunctionName(handler);
        if (fName == null) {
            return;
        }
        FunctionHandlerFactory factory = (FunctionHandlerFactory) server.getCallHandlerFactory();
        if (factory == null) {
            return;
        }
        factory.removeHandler(fName);
        log.info("移除Sap Function：{},Class:{}", fName, handler.getClass().getName());
    }

    protected String getFunctionName(JCoServerFunctionHandler handler) {
        SapFunctionHandler annotation = handler.getClass().getAnnotation(SapFunctionHandler.class);
        if (annotation != null) {
            return annotation.functionName();
        }
        log.error(handler.getClass().getName() + " 注册SapFunctionHandler失败！");
        return null;
    }

    private void startJcoServer(JCoServer server) {
        if (JCoServerState.STARTED.equals(server.getState())) {
            return;
        }
        System.out.println("========启动SAP SERVER========");
        server.start();
    }
}
