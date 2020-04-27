package com.rights.delay.common.interceptor;

import com.rights.delay.common.constant.LogConstants;
import com.rights.delay.common.log.RequestLog;
import com.rights.delay.common.util.TraceIdUtil;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆拦截器，为所有请求添加一个traceId
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2020 /03/14 10:20
 */
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果有上层调用就用上层的ID
        String traceId = request.getHeader(RequestLog.PASSTHROUGH_LOG);
        RequestLog.setMDCLog(traceId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        //调用结束后删除
        RequestLog.clearMDCLog();
    }
}
