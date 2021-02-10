package com.savbiz.ikea.warehouse.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import springfox.documentation.spring.web.plugins.Docket;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(MockitoExtension.class)
class SwaggerConfigTest {

  @InjectMocks
  private SwaggerConfig swaggerConfig;

  @Test
  void test_api() {
    final Docket api = swaggerConfig.api();

    assertThat(api, is(notNullValue()));
  }

}
