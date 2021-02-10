package com.savbiz.ikea.warehouse.repository;

import com.savbiz.ikea.warehouse.PostgresPropertiesInitializer;
import com.savbiz.ikea.warehouse.entity.Product;
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
class ProductRepositoryTestIT {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ProductRepository productRepository;

  @Test
  void test_findAll() {
    final Product product = Product.builder()
        .name("product-1")
        .price(BigDecimal.TEN)
        .currency("EUR")
        .build();

    final Long id = this.entityManager.persistAndGetId(product, Long.class);
    this.entityManager.flush();

    final List<Product> products = this.productRepository.findAll();

    assertThat(products, hasSize(1));
    assertThat(products.get(0).getId(), is(id));
    assertThat(products.get(0).getName(), is("product-1"));
    assertThat(products.get(0).getPrice(), is(BigDecimal.TEN));
    assertThat(products.get(0).getCurrency(), is("EUR"));
  }

  @Test
  void test_deleteById() {
    final Product product = Product.builder()
        .name("product-1")
        .price(BigDecimal.TEN)
        .currency("EUR")
        .build();

    final Long id = this.entityManager.persistAndGetId(product, Long.class);
    this.entityManager.flush();

    this.productRepository.deleteById(id);

    final List<Product> products = this.productRepository.findAll();

    assertThat(products, hasSize(0));
  }

}
