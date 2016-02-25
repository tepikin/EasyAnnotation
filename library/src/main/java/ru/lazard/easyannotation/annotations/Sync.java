package ru.lazard.easyannotation.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * Created by Egor on 25.02.2016.
 */
@Target({METHOD}) @Retention(RetentionPolicy.RUNTIME)
public @interface Sync {
    long delay() default 0;
}
