package net.lelyak.edu.rest.service.impl;


import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lelyak.edu.model.BlogUser;
import net.lelyak.edu.model.Role;
import net.lelyak.edu.rest.repository.UserRepository;
import net.lelyak.edu.utils.exception.NotPresentedInDbException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        BlogUser user = userRepository.findByUserName(username)
                .orElseThrow(NotPresentedInDbException::new);

        log.info("Retrieve user for authentication: {} by user_name: {}", user, username);

        return buildUserForAuthentication(user);
    }

    public org.springframework.security.core.userdetails.User buildUserForAuthentication(BlogUser user) {

        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole().name());
        List<GrantedAuthority> authorities = Lists.newArrayList(grantedAuthority);

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                authorities);
    }
}