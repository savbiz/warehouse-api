package com.savbiz.ikea.warehouse.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleProductResponse {

  @NotNull
  private Long id;

  @NotBlank
  private String name;

  private BigDecimal price;

  private String currency;

  @PositiveOrZero
  @JsonProperty("available-quantity")
  private Integer availableQuantity;

}
