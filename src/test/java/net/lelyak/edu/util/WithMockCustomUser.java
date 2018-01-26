package net.lelyak.edu.util;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Nazar Lelyak.
 */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    String username() default "magelan";

    String password() default "$2a$10$iENgmqajvLIoRQTbNKLKn./VM8bpEmWZDVPeZMPxKk5IBe.ooIltK";

    String email() default "magelan@gmail.com";
}
