package com.rights.delay.service.queue;

/**
 * @author 拓仲 on 2020/3/10
 */
public interface Lifecycle {

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
     */
    boolean isRunning();
}
