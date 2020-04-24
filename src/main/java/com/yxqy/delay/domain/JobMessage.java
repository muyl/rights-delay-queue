package com.yxqy.delay.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 拓仲 on 2020/3/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobMessage implements java.io.Serializable{
    private String topic;
    private String subtopic;
    /**
     * 预留字段
     **/
    private String id;
    private String bizKey;
    private long   delay;
    private long   ttl;
    /***预留字段**/
    private String body;
    private long   createTime = System.currentTimeMillis();
    private int    status     = Status.WaitPut.ordinal();



}
