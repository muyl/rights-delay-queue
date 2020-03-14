package com.yxqy.redis.support;

/**
 * @author 拓仲 on 2020/3/11
 */
public class RedisDistributedLock implements DistributedLock {

    private RedisSupport redisSupport;

    public RedisDistributedLock(RedisSupport redisSupport) {
        this.redisSupport = redisSupport;
    }

    @Override
    public boolean tryLock(String key) {
        return false;
    }

    @Override
    public boolean tryLock(String key, long timeout) {
        return false;
    }

    @Override
    public boolean lock(String key) {
        return false;
    }

    @Override
    public void unlock(String key) {

    }
}
