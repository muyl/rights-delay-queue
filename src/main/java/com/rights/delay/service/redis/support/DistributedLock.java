package com.rights.delay.service.redis.support;

/**
 * Distributed lock
 *
 * @author 拓仲 on 2020/3/11
 */
public interface DistributedLock {
    /**
     * Try lock boolean
     *
     * @param key key
     * @return the boolean
     */
    boolean tryLock(String key);

    /**
     * Try lock boolean
     *
     * @param key key
     * @param timeout timeout
     * @return the boolean
     */
    boolean tryLock(String key, int timeout);

    /**
     * Try lock boolean
     *
     * @param key key
     * @param requestId request id
     * @return the boolean
     */
    boolean tryLock(String key, String requestId);

    /**
     * Try lock boolean
     *
     * @param key key
     * @param requestId request id
     * @param timeout timeout
     * @return the boolean
     */
    boolean tryLock(String key,String requestId, int timeout);

    /**
     * Un lock boolean
     *
     * @param key key
     * @return the boolean
     */
    boolean unLock(String key);

    /**
     * Un lock boolean
     *
     * @param mutex mutex
     * @param requestId request id
     * @return the boolean
     */
    boolean unLock(String mutex, String requestId);
}
