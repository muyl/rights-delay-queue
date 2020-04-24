package com.yxqy.delay.service.redis.support;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author 拓仲 on 2020/3/10
 */
public class RedisSupport {
    private JedisCluster template;

    public JedisCluster getTemplate() {
        return template;
    }

    public void setTemplate(JedisCluster template) {
        this.template = template;
    }

    public void deleteKey(String... key) {
        template.del(key);
    }

    public void set(String k, String v) {
        template.set(k, v);
    }

    public void set(String k, String v, long var3, TimeUnit var5) {
        int seconds = (int) var5.toSeconds(var3);
        template.setex(k, seconds, v);
    }

    public String get(String key) {
        return template.get(key);
    }

    public String getHashKey(String key, String mapKey) {
        return template.hget(key, mapKey);
    }

    public void hashPut(String key, String hashKey, String hashValue) {
        Map<String, String> hashMap = new HashMap<>(16);
        hashMap.put(hashKey, hashValue);
        template.hmset(key, hashMap);
    }

    public void deleteHashKeys(String key, String... keys) {
        template.hdel(key, keys);
    }

    public boolean zadd(String key, String itemKey, double score) {
        return template.zadd(key, score, itemKey) > 0;
    }

    public Long zrem(String key, String itemKey) {
        return template.zrem(key, itemKey);
    }

    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        return template.zrangeByScore(key, min, max, offset, count);
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset,
                                              int count) {
        return template.zrangeByScoreWithScores(key, min, max, offset, count);
    }

    public void rightPush(String readyName, String jobId) {
        template.rpush(readyName,jobId);
    }

    public String leftPop(String key) {
        return template.lpop(key);
    }

    public List<String> lrange(String key, int start, int size) {
        return template.lrange(key, start, size);
    }

    public boolean lrem(String key, String jobId) {
        return template.lrem(key, -1, jobId) > 0;
    }
}
