package com.yxqy.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxqy.domain.RequestParam;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 拓仲(牟云龙) on 2020/2/15
 */

public class WebFilterParam extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        Map<String, String[]> parameterMap = new HashMap<>(request.getParameterMap());
        //修改请求参数
        parameterMap.put("data1",new String[]{"data1"});
        parameterMap.put("data2",new String[]{"data2"});
        parameterMap.put("data3",new String[]{"data3"});
        parameterMap.put("data4",new String[]{"44"});
        ParameterServletRequestWrapper req = new ParameterServletRequestWrapper(request, parameterMap);
        ParameterServletResponseWrapper resp = new ParameterServletResponseWrapper(response);

        //调用对应的controller
        super.doFilter(req,resp,filterChain);

        //对返回参数进行处理
        resp.setContentLength(-1);
        RequestParam requestParam = JSONObject.toJavaObject(JSON.parseObject(new String(resp.getResponseData())), RequestParam.class);
        requestParam.setData1("****");
        requestParam.setData2("****");
        out.print(requestParam.toString());
        out.flush();
        out.close();
    }
}
