package com.rights.delay.service.redis.support;

/**
 * @author 拓仲 on 2020/3/11
 */
public interface DistributedLock {
    boolean tryLock(String key);

    boolean tryLock(String key, int timeout);

    boolean tryLock(String key, String requestId);

    boolean tryLock(String key,String requestId, int timeout);

    boolean unlock(String key);

    boolean unLock(String mutex, String requestId);
}
