package com.yxqy.delay.common.extension;

import java.lang.annotation.*;

/**
 * @author muyl
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SPI {

    /**
     * 默认的实现方式
     */
    String value() default "";
}
