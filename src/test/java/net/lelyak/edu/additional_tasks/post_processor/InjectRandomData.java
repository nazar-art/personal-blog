package net.lelyak.edu.additional_tasks.post_processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Nazar Lelyak.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectRandomData {
    RandomType type();

    int min() default 1;

    int max() default 7;
}
