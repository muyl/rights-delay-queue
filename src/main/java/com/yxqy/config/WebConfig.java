package com.yxqy.config;

import com.yxqy.servlet.WebFilterParam;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 拓仲(牟云龙) on 2020/2/15
 */
@Configuration
public class WebConfig {


    @Bean
    public FilterRegistrationBean WebFilterDemo(){
        //配置过滤器
        FilterRegistrationBean frBean = new FilterRegistrationBean();
        frBean.setFilter(new WebFilterParam());
        frBean.addUrlPatterns("/*");
        frBean.setName("webFilterDemo");
        //springBoot会按照order值的大小，从小到大的顺序来依次过滤。
        frBean.setOrder(0);
        return frBean;
    }
}
