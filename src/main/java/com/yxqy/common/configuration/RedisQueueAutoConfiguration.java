package com.yxqy.common.configuration;

import com.yxqy.redis.JobOperationService;
import com.yxqy.redis.JobOperationServiceImpl;
import com.yxqy.redis.RedisQueueImpl;
import com.yxqy.redis.bucket.BucketQueueManager;
import com.yxqy.redis.ready.ReadyQueueManager;
import com.yxqy.redis.support.RedisDistributedLock;
import com.yxqy.redis.support.RedisQueueProperties;
import com.yxqy.redis.support.RedisSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

/**
 * @author 拓仲 on 2020/3/10
 */
@Configuration
@EnableConfigurationProperties(RedisQueueProperties.class)
@ConditionalOnClass(value = {Jedis.class, RedisQueueImpl.class})
public class RedisQueueAutoConfiguration {

    @Autowired
    private RedisQueueProperties properties;
    @Autowired
    private JedisCluster         template;

    @Bean
    public RedisSupport redisSupport() {
        RedisSupport support = new RedisSupport();
        support.setTemplate(template);
        return support;
    }

    @Bean
    @Autowired
    public RedisDistributedLock redisDistributedLock(RedisSupport redisSupport) {
        return new RedisDistributedLock(redisSupport);
    }

    @Bean
    @Autowired
    public JobOperationService JobOperationService(RedisSupport redisSupport) {
        JobOperationServiceImpl jobOperationService = new JobOperationServiceImpl();
        jobOperationService.setRedisSupport(redisSupport);
        jobOperationService.setProperties(properties);
        return jobOperationService;
    }



    @Bean
    @Autowired
    public BucketQueueManager BucketQueueManager(JobOperationService jobOperationService, RedisDistributedLock lock) {
        BucketQueueManager manager = new BucketQueueManager();
        manager.setProperties(properties);
        manager.setJobOperationService(jobOperationService);
        manager.setLock(lock);
        return manager;
    }

    @Bean
    @Autowired
    public ReadyQueueManager readyQueueManager(JobOperationService jobOperationService, RedisDistributedLock lock) {
        ReadyQueueManager manager = new ReadyQueueManager();
        manager.setProperties(properties);
        manager.setJobOperationService(jobOperationService);
        manager.setLock(lock);
        return manager;
    }

    @Bean
    @Autowired
    public RedisQueueImpl redisQueueImpl(JobOperationService jobOperationService,
                                         BucketQueueManager bucketQueueManager, ReadyQueueManager readyQueueManager) {
        RedisQueueImpl redisQueue = new RedisQueueImpl();
        redisQueue.setProperties(properties);
        redisQueue.setJobOperationService(jobOperationService);
        redisQueue.setBucketQueueManager(bucketQueueManager);
        redisQueue.setReadyQueueManager(readyQueueManager);
        readyQueueManager.setDelayQueue(redisQueue);
        return redisQueue;
    }


}
