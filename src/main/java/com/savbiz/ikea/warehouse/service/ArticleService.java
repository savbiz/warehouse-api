package com.savbiz.ikea.warehouse.service;

import com.savbiz.ikea.warehouse.entity.Article;

import java.util.List;

public interface ArticleService {

  List<Article> saveArticles(List<Article> articles);

}
