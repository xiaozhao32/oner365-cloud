package com.oner365.ldap.config;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import com.oner365.ldap.config.properties.LdapProperties;

/**
 * Ldap Config
 * 
 * @author zhaoyong
 */
@AutoConfiguration
@EnableConfigurationProperties(LdapProperties.class)
@EnableLdapRepositories(basePackages = "com.oner365.ldap.repository")
public class LdapConfiguration {

  @Resource
  private LdapProperties properties;

  @Bean
  ContextSource contextSource() {
    System.setProperty("com.sun.jndi.ldap.object.disableEndpointIdentification", "true");
    
    LdapContextSource source = new LdapContextSource();
    source.setUserDn(properties.getUsername());
    source.setPassword(properties.getPassword());
    source.setBase(properties.getBase());
    source.setUrl(properties.getUrls());
    
    source.setAnonymousReadOnly(false);
    source.setPooled(false);
    source.afterPropertiesSet();
    return source;
  }

  @Bean
  LdapTemplate ldapTemplate(ContextSource contextSource) {
    LdapTemplate template = new LdapTemplate(contextSource);
    template.setIgnorePartialResultException(true);
    return template;
  }
}
