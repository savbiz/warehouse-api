package com.savbiz.ikea.warehouse.service;

import com.savbiz.ikea.warehouse.entity.Article;
import com.savbiz.ikea.warehouse.entity.Product;
import com.savbiz.ikea.warehouse.entity.ProductArticle;
import com.savbiz.ikea.warehouse.exception.InsufficientStockException;
import com.savbiz.ikea.warehouse.repository.ArticleRepository;
import com.savbiz.ikea.warehouse.repository.ProductArticleRepository;
import com.savbiz.ikea.warehouse.repository.ProductRepository;
import com.savbiz.ikea.warehouse.web.dto.request.ProductFileRequest;
import com.savbiz.ikea.warehouse.web.dto.response.ProductResponse;
import com.savbiz.ikea.warehouse.web.dto.response.SimpleProductResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ProductServiceDefaultTest {

  @Mock
  private ProductRepository productRepository;

  @Mock
  private ProductArticleRepository productArticleRepository;

  @Mock
  private ArticleRepository articleRepository;

  @InjectMocks
  private ProductServiceDefault productService;

  @Test
  void test_saveProducts() {
    when(productRepository.save(any(Product.class)))
        .then(returnsFirstArg());
    when(articleRepository.findById(anyLong()))
        .thenReturn(Optional.of(newArticle()));
    when(productArticleRepository.save(any(ProductArticle.class)))
        .then(returnsFirstArg());

    final List<ProductResponse> productResponses = productService.saveProducts(newProductFileRequest());

    assertThat(productResponses, hasSize(1));

    verify(productRepository).save(any(Product.class));
    verify(articleRepository).findById(anyLong());
    verify(productArticleRepository).save(any(ProductArticle.class));
  }

  @Test
  void test_sellProduct() {
    final Long id = 1L;

    when(productRepository.findById(anyLong()))
        .thenReturn(Optional.of(newProduct()));
    when(productArticleRepository.findProductArticlesByProduct(any(Product.class)))
        .thenReturn(List.of(newProductArticle()));

    productService.sellProduct(id);

    verify(productRepository).findById(anyLong());
    verify(productArticleRepository).findProductArticlesByProduct(any(Product.class));
    verify(articleRepository).save(any(Article.class));
    verify(productArticleRepository).deleteById(any());
    verify(productRepository).deleteById(eq(id));
  }

  @Test
  void test_sellProduct_whenStockIsInsufficient() {
    final Long id = 1L;

    final Article article = newArticle();
    article.setAvailableStock(1);

    final ProductArticle productArticle = ProductArticle.builder()
        .product(newProduct())
        .article(article)
        .amountOf(10)
        .build();

    when(productRepository.findById(anyLong()))
        .thenReturn(Optional.of(newProduct()));
    when(productArticleRepository.findProductArticlesByProduct(any(Product.class)))
        .thenReturn(List.of(productArticle));

    assertThrows(InsufficientStockException.class, () -> productService.sellProduct(id));

    verify(productRepository).findById(anyLong());
    verify(productArticleRepository).findProductArticlesByProduct(any(Product.class));
    verify(articleRepository, never()).save(any(Article.class));
    verify(productArticleRepository, never()).deleteById(any());
    verify(productRepository, never()).deleteById(eq(id));
  }

  @Test
  void test_getProduct() {

    when(productRepository.findAll())
        .thenReturn(List.of(newProduct()));
    when(productArticleRepository.findProductArticlesByProduct(any(Product.class)))
        .thenReturn(List.of(newProductArticle()));

    final List<SimpleProductResponse> products = productService.getProducts();

    assertThat(products, hasSize(1));

    verify(productRepository).findAll();
    verify(productArticleRepository).findProductArticlesByProduct(any(Product.class));
  }

  private ProductArticle newProductArticle() {
    return ProductArticle.builder()
        .product(newProduct())
        .article(newArticle())
        .amountOf(10)
        .build();
  }

  private Article newArticle() {
    return Article.builder()
        .id(1L)
        .availableStock(10)
        .build();
  }

  private Product newProduct() {
    return Product.builder()
        .id(1L)
        .name("product-1")
        .build();
  }

  private ProductFileRequest newProductFileRequest() {
    final ProductFileRequest.ProductArticleDto productArticleDto = ProductFileRequest.ProductArticleDto.builder()
        .articleId(1L)
        .amountOf(10)
        .build();

    final ProductFileRequest.ProductDto productDto = ProductFileRequest.ProductDto.builder()
        .name("product-1")
        .containArticles(List.of(productArticleDto))
        .build();

    return ProductFileRequest.builder()
        .products(List.of(productDto))
        .build();
  }


}