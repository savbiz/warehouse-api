package com.savbiz.ikea.warehouse.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.savbiz.ikea.warehouse.service.utils.UTCNowSupplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(MockitoExtension.class)
class WarehouseConfigurationTest {

  @InjectMocks
  private WarehouseConfiguration warehouseConfiguration;

  @Test
  void test_modelMapper() {
    final ModelMapper modelMapper = warehouseConfiguration.modelMapper();

    assertThat(modelMapper, is(notNullValue()));
  }

  @Test
  void test_utcNowSupplier() {
    final UTCNowSupplier utcNowSupplier = warehouseConfiguration.utcNowSupplier();

    assertThat(utcNowSupplier, is(notNullValue()));
  }

  @Test
  void test_objectMapper() {
    final ObjectMapper objectMapper = warehouseConfiguration.objectMapper();

    assertThat(objectMapper, is(notNullValue()));
  }

}
