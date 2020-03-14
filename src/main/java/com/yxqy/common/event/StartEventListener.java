package com.yxqy.common.event;

import com.yxqy.common.config.AppEnvContext;
import com.yxqy.redis.RedisQueueImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author 拓仲 on 2020/3/10
 */
@Configuration
public class StartEventListener implements ApplicationListener<ContextRefreshedEvent> {
    public static final Logger LOGGER = LoggerFactory.getLogger(StartEventListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext ctx = event.getApplicationContext();
        if (ctx != null) {
            RedisQueueImpl redisQueue = ctx.getBean(RedisQueueImpl.class);
            if (!redisQueue.isRunning()) {
                LOGGER.info("starting Queue StandAlone Model ...");
                redisQueue.start();
            }
        }
    }
}
