package com.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yfmacmini001 on 2017/4/1.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisLock {
    String key() default "lock:";
    String fieldKey() default "";
    String value() default "lock";
    int expireTime() default 3;
}
