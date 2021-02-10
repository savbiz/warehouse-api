package com.savbiz.ikea.warehouse.mapper;

import com.savbiz.ikea.warehouse.entity.Article;
import com.savbiz.ikea.warehouse.web.dto.request.InventoryFileRequest;
import com.savbiz.ikea.warehouse.web.dto.response.ArticleResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class MapperTest {

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private Mapper mapper;

  @Test
  void test_toArticleResponse() {
    final Article article = newArticle();

    mapper.toArticleResponse(article);

    verify(modelMapper).map(eq(article), eq(ArticleResponse.class));
  }

  @Test
  void test_toArticleEntities() {
    final InventoryFileRequest.ArticleDto articleDto = InventoryFileRequest.ArticleDto.builder()
        .id(1L)
        .name("art-1")
        .stock(4)
        .build();

    final InventoryFileRequest inventoryFileRequest = InventoryFileRequest.builder()
        .inventory(List.of(articleDto))
        .build();

    final List<Article> articles = mapper.toArticleEntities(inventoryFileRequest);

    assertThat(articles, hasSize(1));
    assertThat(articles.get(0).getId(), is(1L));
    assertThat(articles.get(0).getName(), is("art-1"));
    assertThat(articles.get(0).getAvailableStock(), is(4));
  }

  private Article newArticle() {
    return Article.builder()
        .id(1L)
        .name("article_1")
        .availableStock(4)
        .build();
  }

}