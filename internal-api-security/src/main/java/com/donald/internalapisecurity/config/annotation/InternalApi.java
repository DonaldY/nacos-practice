package com.donald.internalapisecurity.config.annotation;

import java.lang.annotation.*;

/**
 * @author donald
 * @date 2022/01/12
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface InternalApi {
    /**
     * 是否内部接口, 默认 true
     * @return 是否
     */
    boolean value() default true;
}