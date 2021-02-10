package com.savbiz.ikea.warehouse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.ant("/warehouse/api/**/**/**"))
        .build()
        .apiInfo(getApiInfo());
  }

  private ApiInfo getApiInfo() {
    return new ApiInfo("IKEA tech assignment", "Warehouse API", "v1.0",
        "urn:tos",
        new Contact("Savino Bizzoca", "https://www.linkedin.com/in/savino-bizzoca/",
            "sav.bizzoca@gmail.com"),
        "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<>());
  }
}
