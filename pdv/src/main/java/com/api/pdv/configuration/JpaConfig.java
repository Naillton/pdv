package com.api.pdv.configuration;

import com.api.pdv.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.api.pdv.repository")
public class JpaConfig {

    @Bean("userService")
    public UserService userService() {
        return new UserService();
    }
}
