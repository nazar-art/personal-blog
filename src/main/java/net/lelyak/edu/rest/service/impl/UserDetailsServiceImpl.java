package net.lelyak.edu.rest.service.impl;


import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service("blogDTS")
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        BlogUser user = userService.getUser(username);
        List<GrantedAuthority> authorities = buildUserAuthority(user.getRole());

        return buildUserForAuthentication(user, authorities);

    }

    private org.springframework.security.core.userdetails.User buildUserForAuthentication(BlogUser user,
                                                                                          List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Role userRole) {
        Set<GrantedAuthority> setAuths = Stream.of(new SimpleGrantedAuthority(userRole.name()))
                .collect(Collectors.toSet());
        return Lists.newArrayList(setAuths);
    }

}