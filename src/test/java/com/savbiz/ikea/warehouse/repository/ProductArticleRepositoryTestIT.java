package com.savbiz.ikea.warehouse.repository;

import com.savbiz.ikea.warehouse.PostgresPropertiesInitializer;
import com.savbiz.ikea.warehouse.entity.Article;
import com.savbiz.ikea.warehouse.entity.Product;
import com.savbiz.ikea.warehouse.entity.ProductArticle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Testcontainers
@ContextConfiguration(initializers = PostgresPropertiesInitializer.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductArticleRepositoryTestIT {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ProductArticleRepository productArticleRepository;

  @Test
  void test_findAll() {
    final Product product = Product.builder()
        .name("product-1")
        .price(BigDecimal.TEN)
        .currency("EUR")
        .build();

    final Product savedProduct = this.entityManager.persist(product);
    this.entityManager.flush();

    final Article article = Article.builder()
        .name("article-1")
        .availableStock(4)
        .build();

    final Article savedArticle = this.entityManager.persist(article);

    final ProductArticle productArticle = ProductArticle.builder()
        .product(product)
        .article(savedArticle)
        .amountOf(4)
        .build();

    final Long savedProductArticleId = this.entityManager.persistAndGetId(productArticle, Long.class);

    final List<ProductArticle> productArticlesByProduct = this.productArticleRepository.findProductArticlesByProduct(savedProduct);

    assertThat(productArticlesByProduct, hasSize(1));
    assertThat(productArticlesByProduct.get(0).getId(), is(savedProductArticleId));
    assertThat(productArticlesByProduct.get(0).getArticle().getId(), is(savedArticle.getId()));
    assertThat(productArticlesByProduct.get(0).getProduct().getId(), is(savedProduct.getId()));
    assertThat(productArticlesByProduct.get(0).getAmountOf(), is(4));
  }

}