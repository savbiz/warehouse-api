package com.savbiz.ikea.warehouse.mapper;

import com.savbiz.ikea.warehouse.entity.Article;
import com.savbiz.ikea.warehouse.web.dto.request.InventoryFileRequest;
import com.savbiz.ikea.warehouse.web.dto.response.ArticleResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Mapper {

  private final ModelMapper modelMapper;

  public ArticleResponse toArticleResponse(Article article) {
    return modelMapper.map(article, ArticleResponse.class);
  }

  public List<Article> toArticleEntities(InventoryFileRequest inventoryFileRequest) {
    return inventoryFileRequest.getInventory().stream()
        .map(this::createArticle)
        .collect(Collectors.toList());
  }

  private Article createArticle(InventoryFileRequest.ArticleDto articleDto) {
    return Article.builder()
        .id(articleDto.getId())
        .name(articleDto.getName())
        .availableStock(articleDto.getStock())
        .build();
  }
}
