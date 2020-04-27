package com.rights.delay.common.util;

/**
 * Trace id util
 *
 * @author 拓仲 on 2020/3/28
 */
public class TraceIdUtil {
    /**
     * Gets trace id *
     *
     * @return the trace id
     */
    public static String getTraceId() {
        return UUIDUtils.getUUID().toUpperCase();
    }
}
