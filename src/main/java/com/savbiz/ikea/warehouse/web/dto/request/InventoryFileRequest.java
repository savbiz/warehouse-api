package com.savbiz.ikea.warehouse.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryFileRequest {

  @NotEmpty
  List<ArticleDto> inventory;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ArticleDto {
    @NotNull
    @JsonProperty("art_id")
    Long id;

    @NotBlank
    String name;

    @PositiveOrZero
    Integer stock;
  }

}
