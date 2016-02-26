package ru.lazard.easyannotation.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Created by Egor on 25.02.2016.
 */
@Target({METHOD}) @Retention(CLASS)
public @interface NotNull {
}
