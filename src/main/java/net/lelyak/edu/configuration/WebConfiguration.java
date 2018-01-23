package net.lelyak.edu.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * @author Nazar Lelyak.
 */
@Slf4j
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Value("${number.of.posts.per.page}")
    private Integer postsNumber;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        log.debug("Getting number of posts per page: {}", postsNumber);
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setFallbackPageable(new PageRequest(0, postsNumber));
        argumentResolvers.add(resolver);
        super.addArgumentResolvers(argumentResolvers);
    }

}
