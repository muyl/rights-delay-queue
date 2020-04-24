package com.yxqy.delay.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @author 拓仲 on 2020/3/14
 */
public class JobIdGenerator {
    public static final Logger LOGGER = LoggerFactory.getLogger(JobIdGenerator.class);
    public static final int DATACENTER = 2;
    public static final int DEFAULT_MACHINED = 1;
    private static SnowFlake snowFlake = null;

    static {
        int m = machinedId();
        LOGGER.info(" machined {}", m);
        snowFlake = new SnowFlake(DATACENTER, m);
    }

    private static int machinedId() {
        //通过获取IP地址最后一位来获取
        String MACHINED = System.getProperty("machineId");
        if (!StringUtils.isEmpty(MACHINED)) {
            try {
                return Integer.parseInt(MACHINED);
            } catch (Exception e) {
                return DEFAULT_MACHINED;
            }
        }
        return DEFAULT_MACHINED;
    }

    public static long getLongId() {
        return snowFlake.nextId();
    }

    public static String getStringId() {
        return String.valueOf(snowFlake.nextId());
    }


}
