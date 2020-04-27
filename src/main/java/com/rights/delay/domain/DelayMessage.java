package com.rights.delay.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Delay message
 *
 * @author 拓仲 on 2020/3/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DelayMessage {
    /** 主题 */
    private String topic;
    /** 内容 */
    private String body;
    /** 延迟时间 */
    private long   delay;
}
