package com.oner365.neo4j.config;

import org.neo4j.cypherdsl.core.renderer.Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Neo4j Config
 * 
 * @author zhaoyong
 * 
 */
@Configuration
public class DatabaseConfig {

  @Bean
  org.neo4j.cypherdsl.core.renderer.Configuration cypherDslConfiguration() {

    Dialect dialect = Dialect.DEFAULT;
    String neo4jVersion = System.getenv("NEO4J_VERSION");
    if (neo4jVersion == null || neo4jVersion.startsWith("5")) {
      dialect = Dialect.NEO4J_5;
    }

    return org.neo4j.cypherdsl.core.renderer.Configuration.newConfig().withDialect(dialect).build();
  }
}
