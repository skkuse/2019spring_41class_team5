package edu.skku.dealistic.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * Web Configuration.
 * Main Features:
 * - Allow cross origin request
 * - Limit request method
 *
 * @author Junhyun Kim
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final long MAX_AGE_SECS = 1800;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
                .maxAge(MAX_AGE_SECS);
    }

    @Bean
    public Module datatypeHibernateModule() {
        return new Hibernate5Module();
    }

}
