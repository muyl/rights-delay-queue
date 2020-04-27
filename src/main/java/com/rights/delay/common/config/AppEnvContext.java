package com.rights.delay.common.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

/**
 * App env context
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE - 50)
public class AppEnvContext implements EnvironmentAware, ApplicationContextAware {

    private static Environment env;
    private static ApplicationContext ctx;

    /**
     * Gets property *
     *
     * @param key key
     * @return the property
     */
    public static String getProperty(String key) {

        return env.getProperty(key);
    }

    /**
     * Gets property *
     *
     * @param key key
     * @param defaultValue default value
     * @return the property
     */
    public static String getProperty(String key, String defaultValue) {

        String v = getProperty(key);
        return StringUtils.isEmpty(v) ? defaultValue : v;
    }


    /**
     * Gets env *
     *
     * @return the env
     */
    public static Environment getEnv() {
        return env;
    }

    /**
     * Gets ctx *
     *
     * @return the ctx
     */
    public static ApplicationContext getCtx() {
        return ctx;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        env = null;
    }

    @Override
    public void setEnvironment(Environment environment) {
        env = environment;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
}