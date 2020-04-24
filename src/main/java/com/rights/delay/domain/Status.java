package com.rights.delay.domain;

/**
 * @author muyl
 */
public enum Status {
    /** 待加入 */
    WaitPut,
    /** 已经进入延时队列 */
    Delay,
    /** 已经出了延时队列 客户端可以方法此数据 */
    Ready,
    /** 客户端已经处理完数据了 */
    Finish,
    /** 客户端已经把数据删除了 */
    Delete,
    /** 手动恢复重发状态/或者是在实时队列中验证时间出现异常 再次放入buck中 */
    Restore,
}
