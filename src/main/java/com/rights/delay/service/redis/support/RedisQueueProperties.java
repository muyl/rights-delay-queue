package com.rights.delay.service.redis.support;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redis queue properties
 *
 * @author 拓仲 on 2020/3/10
 */
@ConfigurationProperties(prefix = RedisQueueProperties.REDIS_QUEUE_PREFIX)
public class RedisQueueProperties {

    /**
     * REDIS_QUEUE_PREFIX
     */
    public static final String REDIS_QUEUE_PREFIX = "rdmq.rqueue";


    private String name;
    private String prefix = "io.sdmq";
    private String originPool = "pools";
    private String readyName = "ready";
    private int bucketSize = 3;

    /** buck轮询时间 */
    private long buckRoundRobinTime = 300;
    /** ready轮询时间 */
    private long readyRoundRobinTime = 200;

    private boolean cluster = false;


    /**
     * Gets name *
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name *
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets prefix *
     *
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Sets prefix *
     *
     * @param prefix prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Gets origin pool *
     *
     * @return the origin pool
     */
    public String getOriginPool() {
        return originPool;
    }

    /**
     * Sets origin pool *
     *
     * @param originPool origin pool
     */
    public void setOriginPool(String originPool) {
        this.originPool = originPool;
    }

    /**
     * Gets ready name *
     *
     * @return the ready name
     */
    public String getReadyName() {
        return readyName;
    }

    /**
     * Sets ready name *
     *
     * @param readyName ready name
     */
    public void setReadyName(String readyName) {
        this.readyName = readyName;
    }

    /**
     * Gets bucket size *
     *
     * @return the bucket size
     */
    public int getBucketSize() {
        return bucketSize;
    }

    /**
     * Sets bucket size *
     *
     * @param bucketSize bucket size
     */
    public void setBucketSize(int bucketSize) {
        this.bucketSize = bucketSize;
    }

    /**
     * Gets buck round robin time *
     *
     * @return the buck round robin time
     */
    public long getBuckRoundRobinTime() {
        if (buckRoundRobinTime <= 0) {
            buckRoundRobinTime = 500;
        }
        return buckRoundRobinTime;
    }

    /**
     * Sets buck round robin time *
     *
     * @param buckRoundRobinTime buck round robin time
     */
    public void setBuckRoundRobinTime(long buckRoundRobinTime) {
        this.buckRoundRobinTime = buckRoundRobinTime;
    }

    /**
     * Gets ready round robin time *
     *
     * @return the ready round robin time
     */
    public long getReadyRoundRobinTime() {
        return readyRoundRobinTime;
    }

    /**
     * Sets ready round robin time *
     *
     * @param readyRoundRobinTime ready round robin time
     */
    public void setReadyRoundRobinTime(long readyRoundRobinTime) {
        this.readyRoundRobinTime = readyRoundRobinTime;
    }

    /**
     * Is cluster boolean
     *
     * @return the boolean
     */
    public boolean isCluster() {
        return cluster;
    }

    /**
     * Sets cluster *
     *
     * @param cluster cluster
     */
    public void setCluster(boolean cluster) {
        this.cluster = cluster;
    }
}
