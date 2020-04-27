package com.rights.delay.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
    @NotBlank(message = "topic不能为空")
    private String topic;
    /** 内容 */
    @NotBlank(message = "body不能为空")
    private String body;
    /** 延迟时间 */
    @Min(value = 1000, message = "延时时间不能小于1秒")
    private long delay;
}
