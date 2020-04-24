package com.rights.delay.common.extension;

import java.lang.annotation.*;

/**
 * @author muyl
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ExtNamed {

    String value();
}