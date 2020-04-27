package com.rights.delay.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Job id generator
 *
 * @author 拓仲 on 2020/3/14
 */
public class JobIdGenerator {
    /**
     * LOGGER
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(JobIdGenerator.class);
    /**
     * DATACENTER
     */
    public static final int DATACENTER = 2;
    /**
     * DEFAULT_MACHINED
     */
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

    /**
     * Gets long id *
     *
     * @return the long id
     */
    public static long getLongId() {
        return snowFlake.nextId();
    }

    /**
     * Gets string id *
     *
     * @return the string id
     */
    public static String getStringId() {
        return String.valueOf(snowFlake.nextId());
    }


}
