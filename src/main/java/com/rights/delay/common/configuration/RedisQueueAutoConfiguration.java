package com.rights.delay.common.configuration;

import com.rights.delay.service.queue.core.RealTimeQueueProvider;
import com.rights.delay.service.queue.cqp.RealTimeQueueConsumer;
import com.rights.delay.service.redis.JobOperationService;
import com.rights.delay.service.redis.JobOperationServiceImpl;
import com.rights.delay.service.redis.RedisQueueImpl;
import com.rights.delay.service.redis.bucket.BucketQueueManager;
import com.rights.delay.service.redis.ready.ReadyQueueManager;
import com.rights.delay.service.redis.support.RedisDistributedLock;
import com.rights.delay.service.redis.support.RedisQueueProperties;
import com.rights.delay.service.redis.support.RedisSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

/**
 * Redis queue auto configuration
 *
 * @author 拓仲 on 2020/3/10
 */
@Configuration
@EnableConfigurationProperties(RedisQueueProperties.class)
@ConditionalOnClass(value = {Jedis.class, RedisQueueImpl.class})
public class RedisQueueAutoConfiguration {

    @Autowired
    private RedisQueueProperties properties;
    @Autowired
    private JedisCluster template;

    /**
     * Redis support redis support
     *
     * @return the redis support
     */
    @Bean
    public RedisSupport redisSupport() {
        RedisSupport support = new RedisSupport();
        support.setTemplate(template);
        return support;
    }

    /**
     * Consume queue provider real time queue provider
     *
     * @return the real time queue provider
     */
    @Bean
    public RealTimeQueueProvider consumeQueueProvider() {
        return new RealTimeQueueConsumer();
    }

    /**
     * Redis distributed lock redis distributed lock
     *
     * @param redisSupport redis support
     * @return the redis distributed lock
     */
    @Bean
    @Autowired
    public RedisDistributedLock redisDistributedLock(RedisSupport redisSupport) {
        return new RedisDistributedLock(redisSupport);
    }

    /**
     * Job operation service job operation service
     *
     * @param redisSupport redis support
     * @return the job operation service
     */
    @Bean
    @Autowired
    public JobOperationService JobOperationService(RedisSupport redisSupport) {
        JobOperationServiceImpl jobOperationService = new JobOperationServiceImpl();
        jobOperationService.setRedisSupport(redisSupport);
        jobOperationService.setProperties(properties);
        return jobOperationService;
    }


    /**
     * Bucket queue manager bucket queue manager
     *
     * @param jobOperationService job operation service
     * @param lock lock
     * @return the bucket queue manager
     */
    @Bean
    @Autowired
    public BucketQueueManager BucketQueueManager(JobOperationService jobOperationService, RedisDistributedLock lock) {
        BucketQueueManager manager = new BucketQueueManager();
        manager.setProperties(properties);
        manager.setJobOperationService(jobOperationService);
        manager.setLock(lock);
        return manager;
    }

    /**
     * Ready queue manager ready queue manager
     *
     * @param jobOperationService job operation service
     * @param lock lock
     * @param realTimeQueueProvider real time queue provider
     * @return the ready queue manager
     */
    @Bean
    @Autowired
    public ReadyQueueManager readyQueueManager(JobOperationService jobOperationService, RedisDistributedLock lock, RealTimeQueueProvider realTimeQueueProvider) {
        ReadyQueueManager manager = new ReadyQueueManager();
        manager.setProperties(properties);
        manager.setJobOperationService(jobOperationService);
        manager.setLock(lock);
        manager.setRealTimeQueueProvider(realTimeQueueProvider);
        return manager;
    }

    /**
     * Redis queue redis queue
     *
     * @param jobOperationService job operation service
     * @param bucketQueueManager bucket queue manager
     * @param readyQueueManager ready queue manager
     * @return the redis queue
     */
    @Bean
    @Autowired
    public RedisQueueImpl redisQueueImpl(JobOperationService jobOperationService, BucketQueueManager bucketQueueManager, ReadyQueueManager readyQueueManager) {
        RedisQueueImpl redisQueue = new RedisQueueImpl();
        redisQueue.setProperties(properties);
        redisQueue.setJobOperationService(jobOperationService);
        redisQueue.setBucketQueueManager(bucketQueueManager);
        redisQueue.setReadyQueueManager(readyQueueManager);
        readyQueueManager.setDelayQueue(redisQueue);
        return redisQueue;
    }


}
