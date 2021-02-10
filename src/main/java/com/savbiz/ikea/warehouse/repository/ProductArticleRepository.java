package com.savbiz.ikea.warehouse.repository;

import com.savbiz.ikea.warehouse.entity.Product;
import com.savbiz.ikea.warehouse.entity.ProductArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductArticleRepository extends JpaRepository<ProductArticle, Long> {

  @Query
  List<ProductArticle> findProductArticlesByProduct(Product product);

}
