package com.savbiz.ikea.warehouse.service;

import com.savbiz.ikea.warehouse.entity.Article;
import com.savbiz.ikea.warehouse.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ArticleServiceDefault implements ArticleService {

  private final ArticleRepository articleRepository;

  @Override
  public List<Article> saveArticles(List<Article> articles) {
    return articleRepository.saveAll(articles);
  }
}
