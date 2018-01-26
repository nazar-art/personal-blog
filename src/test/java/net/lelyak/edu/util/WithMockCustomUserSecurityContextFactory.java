package net.lelyak.edu.util;

import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.model.Role;
import net.lelyak.edu.rest.service.impl.UserDetailsServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

/**
 * @author Nazar Lelyak.
 */
public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

//        CustomUserDetails principal = new CustomUserDetails(customUser.name(), customUser.username());

        BlogUser principal = BlogUser.builder()
                .userName(customUser.username())
                .password(customUser.password())
                .email(customUser.email())
                .role(Role.USER)
                .build();

        UserDetailsServiceImpl detailsService = new UserDetailsServiceImpl();
        User user = detailsService.buildUserForAuthentication(principal);

        Authentication auth = new UsernamePasswordAuthenticationToken(user, "secret", user.getAuthorities());

        context.setAuthentication(auth);
        return context;
    }
}
