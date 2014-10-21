package com.jorge.directory.app.config;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories("com.jorge.directory.app.repository")
@EnableTransactionManagement
public class DataBaseConfiguration implements EnvironmentAware {

  private final Logger            log = LoggerFactory.getLogger(DataBaseConfiguration.class);

  private RelaxedPropertyResolver propertyResolver;

  @Override
  public void setEnvironment(Environment environment) {
    this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
  }

  @Bean
  public DataSource dataSource() {
    log.debug("Configuring Datasource");
    if (propertyResolver.getProperty("url") == null && propertyResolver.getProperty("databaseName") == null) {
      log.error("Your database connection pool configuration is incorrect! The application");
      throw new ApplicationContextException("Database connection pool is not configured correctly");
    }
    HikariConfig config = new HikariConfig();
    config.setDataSourceClassName(propertyResolver.getProperty("dataSourceClassName"));
    if (propertyResolver.getProperty("url") == null || "".equals(propertyResolver.getProperty("url"))) {
      config.addDataSourceProperty("databaseName", propertyResolver.getProperty("databaseName"));
      config.addDataSourceProperty("serverName", propertyResolver.getProperty("serverName"));
    } else {
      config.addDataSourceProperty("url", propertyResolver.getProperty("url"));
    }
    config.addDataSourceProperty("user", propertyResolver.getProperty("username"));
    config.addDataSourceProperty("password", propertyResolver.getProperty("password"));
    return new HikariDataSource(config);
  }

  @Bean(name = {"org.springframework.boot.autoconfigure.AutoConfigurationUtils.basePackages"})
  public List<String> getBasePackages() {
    List<String> basePackages = new ArrayList<String>();
    basePackages.add("com.jorge.directory.app");
    return basePackages;
  }

  @Bean
  public SpringLiquibase liquibase(DataSource dataSource) {
    log.debug("Configuring Liquibase");
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setDataSource(dataSource);
    liquibase.setChangeLog("classpath:config/liquibase/master.xml");
    liquibase.setContexts("development, production");
    return liquibase;
  }
}
