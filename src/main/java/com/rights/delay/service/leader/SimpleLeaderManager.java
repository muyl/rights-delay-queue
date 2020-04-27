package com.rights.delay.service.leader;


import com.google.common.collect.Lists;
import com.rights.delay.common.configuration.RegistryProperties;
import com.rights.delay.common.util.BlockUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Xs.Tao on 2017/7/28.
 */
public class SimpleLeaderManager implements LeaderManager {

    /**
     * LOGGER
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(SimpleLeaderManager.class);

    private LeaderLatch leaderLatch;

    private CuratorFramework framework;

    private String serverName = "";

    private volatile AtomicBoolean             isLatch   = new AtomicBoolean(false);
    private          List<LeaderLatchListener> listeners = Lists.newArrayList();
    private RegistryProperties properties;

    @Override
    public void init() {
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString(properties.getServerList())
                .retryPolicy(new ExponentialBackoffRetry(properties.getBaseSleepTimeMilliseconds(),
                        properties.getMaxRetries(),
                        properties.getMaxSleepTimeMilliseconds()))
                .namespace(ServerNode.NAMESPACE);
        framework = builder.build();
        framework.start();
        leaderLatch = new LeaderLatch(framework, ServerNode.LEADERLATCH, serverName, LeaderLatch.CloseMode.NOTIFY_LEADER);
        for (LeaderLatchListener listener : listeners) {
            leaderLatch.addListener(listener);
        }
        LOGGER.info("starting Queue Master Slave Model ...");
        start();
    }


    @Override
    public void start() {
        if (isLatch.compareAndSet(false, true)) {
            try {
                LOGGER.info("starting latch....");
                leaderLatch.start();
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void stop() {
        if (isLatch.compareAndSet(true, false)) {
            try {
                BlockUtils.sleep(500);
                LOGGER.info("stop latch....");
                CloseableUtils.closeQuietly(leaderLatch);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean isRunning() {
        return isLatch.get();
    }

    @Override
    public boolean isLeader() {
        return leaderLatch.hasLeadership();
    }

    /**
     * Sets properties *
     *
     * @param properties properties
     */
    public void setProperties(RegistryProperties properties) {
        this.properties = properties;
    }

    /**
     * Add listener *
     *
     * @param leaderLatchListener leader latch listener
     */
    public void addListener(LeaderLatchListener leaderLatchListener) {
        if (!listeners.contains(leaderLatchListener)) {
            listeners.add(leaderLatchListener);
        }

    }

    /**
     * Sets server name *
     *
     * @param serverName server name
     */
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
