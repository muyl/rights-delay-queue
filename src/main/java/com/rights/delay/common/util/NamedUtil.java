package com.rights.delay.common.util;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Named util
 *
 * @author 拓仲 on 2020/3/10
 */
public class NamedUtil {
    /**
     * SPLITE_CHAR
     */
    public static final String SPLITE_CHAR = ":";
    /**
     * LOCK_CHAR
     */
    public static final String LOCK_CHAR   = "LOCK";

    /**
     * Build bucket name string
     *
     * @param prefix prefix
     * @param name name
     * @param index index
     * @return the string
     */
    public static String buildBucketName(String prefix, String name, int index) {
        List<Object> lst = Lists.newArrayList();
        lst.add(prefix);
        lst.add(name);
        lst.add(index);
        return Joiner.on(SPLITE_CHAR).join(lst);
    }

    /**
     * Build pool name string
     *
     * @param prefix prefix
     * @param name name
     * @param pool pool
     * @return the string
     */
    public static String buildPoolName(String prefix, String name, String pool) {
        return Joiner.on(SPLITE_CHAR).join(Lists.newArrayList(prefix, name, pool));
    }

    /**
     * Build real time name string
     *
     * @param prefix prefix
     * @param name name
     * @param readTimeName read time name
     * @return the string
     */
    public static String buildRealTimeName(String prefix, String name, String readTimeName) {
        return Joiner.on(SPLITE_CHAR).join(Lists.newArrayList(prefix, name, readTimeName));
    }

    /**
     * Build lock name string
     *
     * @param prefix prefix
     * @return the string
     */
    public static String buildLockName(String prefix) {
        return Joiner.on(SPLITE_CHAR).join(Lists.newArrayList(prefix.concat(":" + LOCK_CHAR)));
    }
}
