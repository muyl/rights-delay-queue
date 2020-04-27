package com.rights.delay.common.log;

import ch.qos.logback.classic.LoggerContext;
import com.google.common.base.Splitter;
import com.rights.delay.common.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.Serializable;
import java.util.List;

/**
 * Request log
 *
 * @author 拓仲 on 2020/3/31
 */
public class RequestLog implements Serializable {

    /**
     * PASSTHROUGH_LOG
     */
    public static final String PASSTHROUGH_LOG = "REQ-PASSTHROUGH-LOG";
    /**
     * 请求流水
     */
    public static final String REQ_ID = "req.id";

    /**
     * REQ_USERIP
     */
    public static final String REQ_USERIP = "req.userIp";
    /**
     * REQ_CONTEXT
     */
    //请求应用的context
    public static final String REQ_CONTEXT = "req.context";
    /**
     * REQ_CONTEXTIP
     */
    //请求应用的context
    public static final String REQ_CONTEXTIP = "req.contextIp";

    /**
     * REQ_URI
     */
    public static final String REQ_URI = "req.requestURI";
    /**
     * REQ_QUERYSTRING
     */
    public static final String REQ_QUERYSTRING = "req.queryString";


    /**
     * REQ_REFERRER
     */
    public static final String REQ_REFERRER = "req.referrer";

    private static String contextIp = "";

    private static String contextName = "";

    static {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        contextName = loggerContext.getLoggerContextRemoteView().getName();
        contextIp = "127.0.0.1";
    }

    /**
     * Gets log str *
     *
     * @return the log str
     */
    public static String getLogStr() {
        StringBuffer sb = new StringBuffer();

        sb.append(MDC.get(REQ_ID)).append("#");
        sb.append(MDC.get(REQ_USERIP)).append("#");

        //重新获取context和contextIp，解决A->B->C时，B->C传入的context显示为A->C的问题
        sb.append(RequestLog.getContext()).append("#");
        sb.append(RequestLog.getContextIp()).append("#");

        sb.append(MDC.get(REQ_URI)).append("#");
        sb.append(MDC.get(REQ_QUERYSTRING)).append("#");
        sb.append(MDC.get(REQ_REFERRER));

        return sb.toString();
    }

    /**
     * Sets mdc log *
     *
     * @param log log
     */
    public static void setMDCLog(String log) {
        if (StringUtils.isNotBlank(log)) {
            List<String> logList = Splitter.on("#").splitToList(log);
            if (logList.size() == 7) {
                for (int i = 0; i < logList.size(); i++) {
                    MDC.put(REQ_ID, putRequestId(logList.get(0)));
                    MDC.put(REQ_USERIP, logList.get(1));
                    MDC.put(REQ_CONTEXT, logList.get(2));
                    MDC.put(REQ_CONTEXTIP, logList.get(3));
                    MDC.put(REQ_URI, logList.get(4));
                    MDC.put(REQ_QUERYSTRING, logList.get(5));
                    MDC.put(REQ_REFERRER, logList.get(6));
                }
            } else {
                putEmptyMDC();
            }
        } else {
            putEmptyMDC();
        }

    }

    /**
     * Put request id string
     *
     * @param text text
     * @return the string
     */
    public static String putRequestId(String text) {
        if (StringUtils.isEmpty(text)) {
            return UUIDUtils.getUUID();
        }
        return text;
    }

    private static void putEmptyMDC() {
        MDC.put(REQ_ID, UUIDUtils.getUUID());
        MDC.put(REQ_USERIP, "0.0.0.0");
        MDC.put(REQ_CONTEXT, getContext());
        MDC.put(REQ_CONTEXTIP, getContextIp());
        MDC.put(REQ_URI, "");
        MDC.put(REQ_QUERYSTRING, "");
        MDC.put(REQ_REFERRER, "");
    }

    /**
     * Clear mdc log
     */
    public static void clearMDCLog() {
        MDC.clear();
    }

    /**
     * 获取本机应用名称
     *
     * @return context
     */
    public static String getContext() {
        return contextName;
    }

    /**
     * 获取本机应用IP
     *
     * @return context ip
     */
    public static String getContextIp() {
        return contextIp;
    }
}
