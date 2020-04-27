package com.rights.delay.service.queue;

/**
 * Lifecycle
 *
 * @author 拓仲 on 2020/3/10
 */
public interface Lifecycle {

    /**
     * 初始化
     */
    void init();

    /**
     * 启动
     */
    void start();

    /**
     * 停止
     */
    void stop();

    /**
     * 运行
     *
     * @return the boolean
     */
    boolean isRunning();
}
