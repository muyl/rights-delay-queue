package com.rights.delay.common.interceptor;

import com.rights.delay.common.constant.LogConstants;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;
import org.slf4j.MDC;

import java.io.IOException;

/**
 * Http client trace id interceptor
 *
 * @author 拓仲 on 2020/3/28
 */
public class HttpClientTraceIdInterceptor  implements HttpRequestInterceptor {
    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        String traceId = MDC.get(LogConstants.TRACE_ID);
        //当前线程调用中有traceId，则将该traceId进行透传
        if (traceId != null) {
            httpRequest.addHeader(LogConstants.TRACE_ID, traceId);
        }
    }
}