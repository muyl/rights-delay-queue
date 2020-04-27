package com.rights.delay.common.configuration;

import com.rights.delay.common.config.AppEnvContext;
import com.rights.delay.common.util.IpUtils;
import com.rights.delay.service.leader.LeaderManager;
import com.rights.delay.service.leader.LeaderWorkListener;
import com.rights.delay.service.leader.ServerNode;
import com.rights.delay.service.leader.SimpleLeaderManager;
import com.rights.delay.service.redis.RedisQueueImpl;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.zookeeper.server.ZooKeeperServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * Leader auto configuration
 *
 * @author Xs.Tao
 * @date 2017 /7/28
 */
@Configuration
@EnableConfigurationProperties(RegistryProperties.class)
@ConditionalOnProperty(prefix = RegistryProperties.SDMQ_REGISTRY_PREFIX, value = "enable", havingValue = "true")
@ConditionalOnClass(value = {ZooKeeperServer.class, CuratorFrameworkFactory.class})
@Order(value = Ordered.LOWEST_PRECEDENCE + 50)
public class LeaderAutoConfiguration {

    @Autowired
    private RegistryProperties registryProperties;


    /**
     * Leader latch listener leader latch listener
     *
     * @param redisQueueImpl redis queue
     * @return the leader latch listener
     */
    @Bean
    @Autowired
    @ConditionalOnMissingBean
    public LeaderLatchListener leaderLatchListenerImpl(RedisQueueImpl redisQueueImpl) {
        LeaderWorkListener listener = new LeaderWorkListener();
        listener.setQueue(redisQueueImpl);
        return listener;
    }

    /**
     * Leader manager leader manager
     *
     * @param leaderLatchListener leader latch listener
     * @return the leader manager
     */
    @Bean(name = "simpleLeaderManager", initMethod = "init", destroyMethod = "stop")
    @Autowired
    @ConditionalOnMissingBean
    public LeaderManager leaderManager(LeaderLatchListener leaderLatchListener) {
        SimpleLeaderManager slm = new SimpleLeaderManager();
        slm.setProperties(registryProperties);
        slm.addListener(leaderLatchListener);
        ServerNode.NAMESPACE = registryProperties.getNamespace();
        slm.setServerName(IpUtils.getIp() + ":" + AppEnvContext.getProperty("server.port"));
        return slm;
    }
}
