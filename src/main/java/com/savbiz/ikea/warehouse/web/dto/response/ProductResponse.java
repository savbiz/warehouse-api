package com.savbiz.ikea.warehouse.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

  @NotNull
  private Long id;

  @NotBlank
  private String name;

  private BigDecimal price;

  private String currency;

  private List<ProductArticle> productArticles = new ArrayList<>();

  @Builder
  public static class ProductArticle {
    @NotNull
    @JsonProperty("article_id")
    Long id;

    @NotNull
    @JsonProperty("amount_of")
    Integer amount;
  }

}
