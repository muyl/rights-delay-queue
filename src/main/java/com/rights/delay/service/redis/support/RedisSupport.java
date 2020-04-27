package com.rights.delay.service.redis.support;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis support
 *
 * @author 拓仲 on 2020/3/10
 */
public class RedisSupport {
    private JedisCluster template;

    /**
     * Gets template *
     *
     * @return the template
     */
    public JedisCluster getTemplate() {
        return template;
    }

    /**
     * Sets template *
     *
     * @param template template
     */
    public void setTemplate(JedisCluster template) {
        this.template = template;
    }

    /**
     * Delete key *
     *
     * @param key key
     */
    public void deleteKey(String... key) {
        template.del(key);
    }

    /**
     * Set *
     *
     * @param k k
     * @param v v
     */
    public void set(String k, String v) {
        template.set(k, v);
    }

    /**
     * Set *
     *
     * @param k k
     * @param v v
     * @param var3 var 3
     * @param var5 var 5
     */
    public void set(String k, String v, long var3, TimeUnit var5) {
        int seconds = (int) var5.toSeconds(var3);
        template.setex(k, seconds, v);
    }

    /**
     * Get string
     *
     * @param key key
     * @return the string
     */
    public String get(String key) {
        return template.get(key);
    }

    /**
     * Gets hash key *
     *
     * @param key key
     * @param mapKey map key
     * @return the hash key
     */
    public String getHashKey(String key, String mapKey) {
        return template.hget(key, mapKey);
    }

    /**
     * Hash put *
     *
     * @param key key
     * @param hashKey hash key
     * @param hashValue hash value
     */
    public void hashPut(String key, String hashKey, String hashValue) {
        Map<String, String> hashMap = new HashMap<>(16);
        hashMap.put(hashKey, hashValue);
        template.hmset(key, hashMap);
    }

    /**
     * Delete hash keys *
     *
     * @param key key
     * @param keys keys
     */
    public void deleteHashKeys(String key, String... keys) {
        template.hdel(key, keys);
    }

    /**
     * Zadd boolean
     *
     * @param key key
     * @param itemKey item key
     * @param score score
     * @return the boolean
     */
    public boolean zadd(String key, String itemKey, double score) {
        return template.zadd(key, score, itemKey) > 0;
    }

    /**
     * Zrem long
     *
     * @param key key
     * @param itemKey item key
     * @return the long
     */
    public Long zrem(String key, String itemKey) {
        return template.zrem(key, itemKey);
    }

    /**
     * Zrange by score set
     *
     * @param key key
     * @param min min
     * @param max max
     * @param offset offset
     * @param count count
     * @return the set
     */
    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        return template.zrangeByScore(key, min, max, offset, count);
    }

    /**
     * Zrange by score with scores set
     *
     * @param key key
     * @param min min
     * @param max max
     * @param offset offset
     * @param count count
     * @return the set
     */
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset,
                                              int count) {
        return template.zrangeByScoreWithScores(key, min, max, offset, count);
    }

    /**
     * Right push *
     *
     * @param readyName ready name
     * @param jobId job id
     */
    public void rightPush(String readyName, String jobId) {
        template.rpush(readyName,jobId);
    }

    /**
     * Left pop string
     *
     * @param key key
     * @return the string
     */
    public String leftPop(String key) {
        return template.lpop(key);
    }

    /**
     * Lrange list
     *
     * @param key key
     * @param start start
     * @param size size
     * @return the list
     */
    public List<String> lrange(String key, int start, int size) {
        return template.lrange(key, start, size);
    }

    /**
     * Lrem boolean
     *
     * @param key key
     * @param jobId job id
     * @return the boolean
     */
    public boolean lrem(String key, String jobId) {
        return template.lrem(key, -1, jobId) > 0;
    }


    /**
     * Set string
     *
     * @param mutex mutex
     * @param requestId request id
     * @param nx nx
     * @param ex ex
     * @param defaultTimeOut default time out
     * @return the string
     */
    public String set(String mutex, String requestId, String nx, String ex, int defaultTimeOut) {
        return template.set(mutex, requestId, nx, ex, defaultTimeOut);
    }

    /**
     * Eval object
     *
     * @param script script
     * @param singletonList singleton list
     * @param singletonList1 singleton list 1
     * @return the object
     */
    public Object eval(String script, List<String> singletonList, List<String> singletonList1) {
        return template.eval(script,singletonList,singletonList1);
    }
}
