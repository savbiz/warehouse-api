package com.savbiz.ikea.warehouse.repository;

import com.savbiz.ikea.warehouse.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

}
