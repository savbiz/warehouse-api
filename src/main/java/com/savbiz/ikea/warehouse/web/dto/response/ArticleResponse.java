package com.savbiz.ikea.warehouse.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponse {

  @NotNull
  private Long id;

  @NotNull
  private String name;

  @NotNull
  @JsonProperty("available-stock")
  private Integer availableStock;
}
