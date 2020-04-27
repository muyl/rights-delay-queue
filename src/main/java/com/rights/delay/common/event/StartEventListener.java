package com.rights.delay.common.event;

import com.rights.delay.common.config.AppEnvContext;
import com.rights.delay.service.redis.RedisQueueImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Start event listener
 *
 * @author 拓仲 on 2020/3/10
 */
@Configuration
public class StartEventListener implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * LOGGER
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(StartEventListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext ctx = event.getApplicationContext();
        if (ctx != null) {
            String regEnable = AppEnvContext.getProperty("rdmq.registry.enable", "false");
            RedisQueueImpl redisQueue = ctx.getBean(RedisQueueImpl.class);
            if (!redisQueue.isRunning() && !Boolean.parseBoolean(regEnable)) {
                LOGGER.info("starting Queue StandAlone Model ...");
                redisQueue.start();
            }
        }
    }
}
