package net.lelyak.edu.configuration;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.sql.DataSource;

@Configuration
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private AccessDeniedHandler accessDeniedHandler;
    private DataSource dataSource;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/about", "/registration", "/403", "/500").permitAll()
                .antMatchers("/home/**").hasAnyRole("USER")
                .antMatchers("/user/**").hasAnyRole("USER")
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .httpBasic()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler).accessDeniedPage("/403");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("carlos")
                .password("$2a$10$fB4clVkfjAba02mwIJidqOiCGv6tPV7n5/vkbfEfQAmsu6WMhF5Bi")
                .roles("USER")
                .and()
                .withUser("chris")
                .password("$2a$10$fB4clVkfjAba02mwIJidqOiCGv6tPV7n5/vkbfEfQAmsu6WMhF5Bi")
                .roles("USER")
        .and().passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
