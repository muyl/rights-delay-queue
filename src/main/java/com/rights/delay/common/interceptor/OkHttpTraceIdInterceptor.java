package com.rights.delay.common.interceptor;

import com.rights.delay.common.constant.LogConstants;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.MDC;

import java.io.IOException;

/**
 * @author 拓仲 on 2020/3/28
 */
public class OkHttpTraceIdInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        String traceId = MDC.get(LogConstants.TRACE_ID);
        Request request = null;
        if (traceId != null) {
            request = chain.request().newBuilder()
                    .addHeader(LogConstants.TRACE_ID, traceId)
                    .build();
        }

        return chain.proceed(request);
    }
}