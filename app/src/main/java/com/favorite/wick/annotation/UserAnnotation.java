package com.favorite.wick.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Weimin on 5/30/2016.
 */
@Target(value = {ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserAnnotation {

    public int id() default 0;

    public String name() default "";

    public String age() default "";

    public String gender() default "M";
}

