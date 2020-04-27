package com.rights.delay.common.interceptor;

import com.rights.delay.common.constant.LogConstants;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * RestTemplate添加 traceId拦截器
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2020 /03/19 15:59
 */
public class RestTemplateTraceIdInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        String traceId = MDC.get(LogConstants.TRACE_ID);
        if (traceId != null) {
            httpRequest.getHeaders().add(LogConstants.TRACE_ID, traceId);
        }

        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
