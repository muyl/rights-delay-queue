package com.yxqy.service.redis.support;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 拓仲 on 2020/3/10
 */
@ConfigurationProperties(prefix = RedisQueueProperties.REDIS_QUEUE_PREFIX)
public class RedisQueueProperties {

    public static final String REDIS_QUEUE_PREFIX = "dlmq.rqueue";


    private String name;
    private String prefix     = "io.sdmq";
    private String originPool = "pools";
    private String readyName  = "ready";
    private int    bucketSize = 3;

    /** buck轮询时间 */
    private long buckRoundRobinTime  = 300;
    /** ready轮询时间 */
    private long readyRoundRobinTime = 200;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getOriginPool() {
        return originPool;
    }

    public void setOriginPool(String originPool) {
        this.originPool = originPool;
    }

    public String getReadyName() {
        return readyName;
    }

    public void setReadyName(String readyName) {
        this.readyName = readyName;
    }

    public int getBucketSize() {
        return bucketSize;
    }

    public void setBucketSize(int bucketSize) {
        this.bucketSize = bucketSize;
    }

    public long getBuckRoundRobinTime() {
        if (buckRoundRobinTime <= 0) {
            buckRoundRobinTime = 500;
        }
        return buckRoundRobinTime;
    }

    public void setBuckRoundRobinTime(long buckRoundRobinTime) {
        this.buckRoundRobinTime = buckRoundRobinTime;
    }

    public long getReadyRoundRobinTime() {
        return readyRoundRobinTime;
    }

    public void setReadyRoundRobinTime(long readyRoundRobinTime) {
        this.readyRoundRobinTime = readyRoundRobinTime;
    }
}
