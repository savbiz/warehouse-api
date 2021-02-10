package com.savbiz.ikea.warehouse.service;

import com.savbiz.ikea.warehouse.entity.Article;
import com.savbiz.ikea.warehouse.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ArticleServiceDefaultTest {

  @Mock
  private ArticleRepository articleRepository;

  @InjectMocks
  private ArticleServiceDefault articleService;

  @Test
  void test_saveArticles() {
    final Article article = mock(Article.class);

    when(articleRepository.saveAll(anyList()))
        .then(returnsFirstArg());

    articleService.saveArticles(List.of(article));

    verify(articleRepository).saveAll(anyList());
  }

}