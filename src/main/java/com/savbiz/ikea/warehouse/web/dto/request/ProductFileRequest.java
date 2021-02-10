package com.savbiz.ikea.warehouse.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFileRequest {

  @NotEmpty
  List<ProductDto> products;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ProductDto {
    @NotBlank
    String name;

    @NotNull
    @JsonProperty("contain_articles")
    List<ProductArticleDto> containArticles;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ProductArticleDto {
    @NotBlank
    @JsonProperty("art_id")
    Long articleId;

    @NotNull
    @JsonProperty("amount_of")
    Integer amountOf;
  }

}
