package com.savbiz.ikea.warehouse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = {PostgresPropertiesInitializer.class})
class WarehouseApplicationTests {

  @Test
  void contextLoads() {
  }

}
