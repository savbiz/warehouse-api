package com.savbiz.ikea.warehouse.service;

import com.savbiz.ikea.warehouse.entity.Article;
import com.savbiz.ikea.warehouse.entity.Product;
import com.savbiz.ikea.warehouse.entity.ProductArticle;
import com.savbiz.ikea.warehouse.exception.ArticleNotFoundException;
import com.savbiz.ikea.warehouse.exception.InsufficientStockException;
import com.savbiz.ikea.warehouse.exception.ProductNotFoundException;
import com.savbiz.ikea.warehouse.repository.ArticleRepository;
import com.savbiz.ikea.warehouse.repository.ProductArticleRepository;
import com.savbiz.ikea.warehouse.repository.ProductRepository;
import com.savbiz.ikea.warehouse.web.dto.request.ProductFileRequest;
import com.savbiz.ikea.warehouse.web.dto.response.ProductResponse;
import com.savbiz.ikea.warehouse.web.dto.response.SimpleProductResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ProductServiceDefault implements ProductService {

  private final ProductRepository productRepository;
  private final ProductArticleRepository productArticleRepository;
  private final ArticleRepository articleRepository;

  @Override
  @Transactional
  public List<ProductResponse> saveProducts(ProductFileRequest productFileRequest) {
    return productFileRequest.getProducts().stream()
        .map(this::processProduct)
        .collect(Collectors.toUnmodifiableList());
  }

  private ProductResponse processProduct(ProductFileRequest.ProductDto productDto) {
    final Product product = Product.builder()
        .name(productDto.getName())
        .build();

    final Product savedProduct = productRepository.save(product);

    final List<ProductResponse.ProductArticle> productArticles = productDto.getContainArticles().stream()
        .map(productArticleDto -> processProductArticle(savedProduct, productArticleDto))
        .collect(Collectors.toList());

    return ProductResponse.builder()
        .id(savedProduct.getId())
        .name(savedProduct.getName())
        .productArticles(productArticles)
        .price(savedProduct.getPrice())
        .currency(savedProduct.getCurrency()).build();
  }

  private ProductResponse.ProductArticle processProductArticle(Product savedProduct, ProductFileRequest.ProductArticleDto productArticleDto) {
    final Long articleId = productArticleDto.getArticleId();

    final Article article = articleRepository.findById(articleId)
        .orElseThrow(ArticleNotFoundException.supplier(String.format("Article not found for id '%s'!", articleId)));

    final ProductArticle productArticle = ProductArticle.builder()
        .product(savedProduct)
        .article(article)
        .amountOf(productArticleDto.getAmountOf())
        .build();

    final ProductArticle savedProductArticle = productArticleRepository.save(productArticle);

    return ProductResponse.ProductArticle.builder()
        .id(savedProductArticle.getId())
        .amount(savedProductArticle.getAmountOf())
        .build();
  }

  @Override
  public List<SimpleProductResponse> getProducts() {
    final List<Product> allProducts = productRepository.findAll();

    return allProducts.stream()
        .map(this::buildSimpleProductResponse)
        .collect(Collectors.toList());
  }

  private SimpleProductResponse buildSimpleProductResponse(Product product) {
    final List<ProductArticle> productArticlesList = productArticleRepository.findProductArticlesByProduct(product);

    final Integer availableQuantity = productArticlesList.isEmpty() ? 0 : getAvailableQuantity(productArticlesList);

    return SimpleProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .price(product.getPrice())
        .currency(product.getCurrency())
        .availableQuantity(availableQuantity)
        .build();
  }

  private Integer getAvailableQuantity(List<ProductArticle> productArticles) {
    return productArticles
        .stream()
        .map(productArticle -> productArticle.getArticle().getAvailableStock() / productArticle.getAmountOf())
        .min(Integer::compare)
        .orElseThrow();
  }

  @Override
  @Transactional
  public void sellProduct(Long id) {
    final Product product = productRepository.findById(id)
        .orElseThrow(ProductNotFoundException.supplier(String.format("Product not found for id '%s'!", id)));

    productArticleRepository.findProductArticlesByProduct(product)
        .forEach(this::updateProductArticle);

    productRepository.deleteById(id);
  }

  private void updateProductArticle(ProductArticle productArticle) throws InsufficientStockException {
    final Article article = productArticle.getArticle();
    final Integer amountOfArticleNeededForProduct = productArticle.getAmountOf();
    final Integer availableArticleStock = article.getAvailableStock();

    if (availableArticleStock >= amountOfArticleNeededForProduct) {
      article.setAvailableStock(availableArticleStock - amountOfArticleNeededForProduct);
      articleRepository.save(article);
      productArticleRepository.deleteById(productArticle.getId());
    } else {
      final Long productId = productArticle.getProduct().getId();
      final Long articleId = productArticle.getArticle().getId();

      final String errorMsg = String.format("Insufficient stock of article '%s' for product '%s'!", productId, articleId);
      log.error(errorMsg);
      throw new InsufficientStockException(errorMsg);
    }
  }
}
