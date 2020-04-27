package com.rights.delay.common.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.rights.delay.common.interceptor.LogInterceptor;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Custom web configure
 *
 * @author 拓仲 on 2020/3/15
 */
@Configuration
public class CustomWebConfigure implements WebMvcConfigurer {

    /**
     * Custom converters http message converters
     *
     * @return the http message converters
     */
    @Bean
    public HttpMessageConverters customConverters(){
        return new HttpMessageConverters(new FastJsonHttpMessageConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**");
    }
}
