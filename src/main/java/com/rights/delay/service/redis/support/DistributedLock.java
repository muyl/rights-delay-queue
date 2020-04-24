package com.rights.delay.service.redis.support;

/**
 * @author 拓仲 on 2020/3/11
 */
public interface DistributedLock {
    boolean tryLock(String key);

    boolean tryLock(String key, long timeout);

    boolean lock(String key);

    void unlock(String key);
}
