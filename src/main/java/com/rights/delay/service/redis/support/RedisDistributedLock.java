package com.rights.delay.service.redis.support;

import java.util.Collections;

/**
 * Redis distributed lock
 *
 * @author 拓仲 on 2020/3/11
 */
public class RedisDistributedLock implements DistributedLock {

    private static final Long   RELEASE_SUCCESS  = 1L;
    private static final String LOCK_SUCCESS     = "OK";
    private static final int    DEFAULT_TIME_OUT = 30 * 60;

    private RedisSupport redisSupport;

    /**
     * Redis distributed lock
     *
     * @param redisSupport redis support
     */
    public RedisDistributedLock(RedisSupport redisSupport) {
        this.redisSupport = redisSupport;
    }

    @Override
    public boolean tryLock(String mutex) {
        String result = redisSupport.set(mutex, "1", "nx", "ex", DEFAULT_TIME_OUT);
        return LOCK_SUCCESS.equals(result);
    }

    @Override
    public boolean tryLock(String mutex, int expireTime) {
        String result = redisSupport.set(mutex, "1", "nx", "ex", expireTime);
        return LOCK_SUCCESS.equals(result);
    }

    @Override
    public boolean tryLock(String mutex,String requestId) {
        String result = redisSupport.set(mutex, requestId, "nx", "ex", DEFAULT_TIME_OUT);
        return LOCK_SUCCESS.equals(result);
    }

    @Override
    public boolean tryLock(String mutex,String requestId, int expireTime) {
        String result = redisSupport.set(mutex, requestId, "nx", "ex", expireTime);
        return LOCK_SUCCESS.equals(result);
    }



    @Override
    public boolean unLock(String mutex) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = redisSupport.eval(script, Collections.singletonList(mutex), Collections.singletonList("1"));
        return RELEASE_SUCCESS.equals(result);
    }

    /**
     * 释放分布式锁
     *
     * @param mutex 锁
     * @return 是否释放成功
     */
    @Override
    public boolean unLock(String mutex,String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = redisSupport.eval(script, Collections.singletonList(mutex), Collections.singletonList(requestId));
        return RELEASE_SUCCESS.equals(result);

    }
}
