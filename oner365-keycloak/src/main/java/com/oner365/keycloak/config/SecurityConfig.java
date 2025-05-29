package com.oner365.keycloak.config;

import java.io.IOException;

import javax.annotation.Resource;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Keycloak Configuration
 *
 * @author zhaoyong
 */
@Configuration
@EnableWebSecurity
@KeycloakConfiguration
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Resource
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        KeycloakAuthenticationProvider provider = keycloakAuthenticationProvider();
        provider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(provider);
    }

    @Bean
    KeycloakConfigResolver fileKeycloakConfigResolver() {
        return request -> {
            ClassPathResource classPathResource = new ClassPathResource("keycloak.json");
            AdapterConfig adapterConfig = null;
            try {
                adapterConfig = new ObjectMapper().readValue(classPathResource.getFile(), AdapterConfig.class);
            }
            catch (IOException e) {
                logger.error("IOException Exception", e);
            }
            return KeycloakDeploymentBuilder.build(adapterConfig);
        };

    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.authorizeHttpRequests().antMatchers("/admin/**").hasRole("ADMIN").anyRequest().permitAll();
    }

}