package com.rights.delay.common.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Objects;

/**
 * Created by Xs.Tao on 2017/7/28.
 */
@ConfigurationProperties(prefix = RegistryProperties.SDMQ_REGISTRY_PREFIX)
public class RegistryProperties {

    /**
     * SDMQ_REGISTRY_PREFIX
     */
    public static final String SDMQ_REGISTRY_PREFIX = "rdmq.registry";


    private String enable = Objects.toString(Boolean.FALSE);
    private String serverList;

    private int maxRetries = 100;

    private int maxSleepTimeMilliseconds;

    private int baseSleepTimeMilliseconds;

    private String namespace = "io-sdmq";

    /**
     * Gets server list *
     *
     * @return the server list
     */
    public String getServerList() {
        return serverList;
    }

    /**
     * Sets server list *
     *
     * @param serverList server list
     */
    public void setServerList(String serverList) {
        this.serverList = serverList;
    }

    /**
     * Gets max retries *
     *
     * @return the max retries
     */
    public int getMaxRetries() {
        return maxRetries;
    }

    /**
     * Sets max retries *
     *
     * @param maxRetries max retries
     */
    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    /**
     * Gets max sleep time milliseconds *
     *
     * @return the max sleep time milliseconds
     */
    public int getMaxSleepTimeMilliseconds() {
        return maxSleepTimeMilliseconds;
    }

    /**
     * Sets max sleep time milliseconds *
     *
     * @param maxSleepTimeMilliseconds max sleep time milliseconds
     */
    public void setMaxSleepTimeMilliseconds(int maxSleepTimeMilliseconds) {
        this.maxSleepTimeMilliseconds = maxSleepTimeMilliseconds;
    }

    /**
     * Gets base sleep time milliseconds *
     *
     * @return the base sleep time milliseconds
     */
    public int getBaseSleepTimeMilliseconds() {
        return baseSleepTimeMilliseconds;
    }

    /**
     * Sets base sleep time milliseconds *
     *
     * @param baseSleepTimeMilliseconds base sleep time milliseconds
     */
    public void setBaseSleepTimeMilliseconds(int baseSleepTimeMilliseconds) {
        this.baseSleepTimeMilliseconds = baseSleepTimeMilliseconds;
    }

    /**
     * Gets enable *
     *
     * @return the enable
     */
    public String getEnable() {
        return enable;
    }

    /**
     * Sets enable *
     *
     * @param enable enable
     */
    public void setEnable(String enable) {
        this.enable = enable;
    }

    /**
     * Gets namespace *
     *
     * @return the namespace
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * Sets namespace *
     *
     * @param namespace namespace
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
