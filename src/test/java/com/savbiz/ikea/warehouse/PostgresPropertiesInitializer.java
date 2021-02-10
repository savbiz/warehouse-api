package com.savbiz.ikea.warehouse;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class PostgresPropertiesInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  @Override
  public void initialize(ConfigurableApplicationContext context) {
    TestPropertyValues.of(
        "spring.datasource.url=" + "jdbc:tc:postgresql:///order",
        "spring.datasource.password=" + "pass",
        "spring.datasource.username=" + "password",
        "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver"
    ).applyTo(context.getEnvironment());
  }
}
