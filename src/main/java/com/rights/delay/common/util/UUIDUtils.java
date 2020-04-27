package com.rights.delay.common.util;


import java.util.UUID;

/**
 * Uuid utils
 *
 * @author tony
 * @version 1.0
 */
public final class UUIDUtils {

    private UUIDUtils() {
    }

    /**
     * 获取uuid
     *
     * @return UUID uuid
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }
}
