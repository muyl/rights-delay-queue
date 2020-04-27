package com.rights.delay.common.event;

import com.rights.delay.service.redis.RedisQueueImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

/**
 * Failed event listener
 *
 * @author 拓仲 on 2020/3/10
 */
@Configuration
public class FailedEventListener implements ApplicationListener<ApplicationFailedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FailedEventListener.class);
    @Override
    public void onApplicationEvent(ApplicationFailedEvent event) {
        ApplicationContext ctx = event.getApplicationContext();
        if (ctx != null) {
            RedisQueueImpl redisQueue = ctx.getBean(RedisQueueImpl.class);
            if (redisQueue != null && redisQueue.isRunning()) {
                LOGGER.info("stop Queue StandAlone Model ...");
                redisQueue.stop();
            }
        }
    }
}
