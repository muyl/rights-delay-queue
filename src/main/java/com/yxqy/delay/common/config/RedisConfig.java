package com.yxqy.delay.common.config;

import lombok.Data;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.*;

/**
 * @author 拓仲 on 2020/3/10
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedisConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    private List<String>         clusterNodes;
    private String               auth;
    private Integer              maxTotal;
    private Integer              minIdle;
    private Integer              maxIdle;
    private Long                 maxWaitMillis;
    private int                  commandTimeout;


    @Bean
    public JedisCluster jedisCluster() {
        Set<HostAndPort> nodes = new HashSet<>();
        for (String clusterNode : clusterNodes) {
            String[] ipPortPair = clusterNode.split(":");
            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.parseInt(ipPortPair[1].trim())));
        }

        if (!nodes.isEmpty()) {
            String password = getAuth(auth);
            logger.info("redis password:{}", password);
            GenericObjectPoolConfig pool = new GenericObjectPoolConfig();
            pool.setMaxTotal(maxTotal);
            pool.setMinIdle(minIdle);
            pool.setMaxIdle(maxIdle);
            pool.setMaxWaitMillis(maxWaitMillis);
            return new JedisCluster(nodes, commandTimeout, commandTimeout, 5, password, pool);
        }
        return null;
    }

    private String getAuth(String auth) {
        return "".equals(auth) ? null : auth;
    }


}
