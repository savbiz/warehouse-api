package com.savbiz.ikea.warehouse.web.controller;

import com.savbiz.ikea.warehouse.exception.ArticleNotFoundException;
import com.savbiz.ikea.warehouse.exception.InsufficientStockException;
import com.savbiz.ikea.warehouse.exception.ProductNotFoundException;
import com.savbiz.ikea.warehouse.service.utils.UTCNowSupplier;
import com.savbiz.ikea.warehouse.web.dto.error.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.WebRequest;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class WarehouseControllerAdviceTest {

  private static final ZonedDateTime NOW = ZonedDateTime.now(ZoneId.of("UTC"));

  @Mock
  private UTCNowSupplier utcNowSupplier;

  @Mock
  private WebRequest webRequest;

  @InjectMocks
  private WarehouseControllerAdvice warehouseControllerAdvice;

  @BeforeEach
  public void setUp() {
    when(utcNowSupplier.get()).thenReturn(NOW);
    when(webRequest.getDescription(false)).thenReturn("test");
  }

  @Test
  void test_handleArticleNotFoundException() {
    final ArticleNotFoundException exception = new ArticleNotFoundException("Article not found!");

    final ResponseEntity<ErrorResponse> responseEntity = warehouseControllerAdvice.handleArticleNotFoundException(exception, webRequest);

    assertThat(responseEntity, is(notNullValue()));
    assertThat(responseEntity.getStatusCode().value(), is(HttpStatus.NOT_FOUND.value()));
    assertThat(responseEntity.getBody(), is(notNullValue()));
    assertThat(responseEntity.getBody().getErrorCode(), is("W-400a"));
    assertThat(responseEntity.getBody().getErrorMessage(), is(notNullValue()));
    assertThat(responseEntity.getBody().getMessages(), is(notNullValue()));
    assertThat(responseEntity.getBody().getTimestamp(), is(NOW));
  }

  @Test
  void test_handleProductNotFoundException() {
    final ProductNotFoundException exception = new ProductNotFoundException("Product not found!");

    final ResponseEntity<ErrorResponse> responseEntity = warehouseControllerAdvice.handleProductNotFoundException(exception, webRequest);

    assertThat(responseEntity, is(notNullValue()));
    assertThat(responseEntity.getStatusCode().value(), is(HttpStatus.NOT_FOUND.value()));
    assertThat(responseEntity.getBody(), is(notNullValue()));
    assertThat(responseEntity.getBody().getErrorCode(), is("W-400b"));
    assertThat(responseEntity.getBody().getErrorMessage(), is(notNullValue()));
    assertThat(responseEntity.getBody().getMessages(), is(notNullValue()));
    assertThat(responseEntity.getBody().getTimestamp(), is(NOW));
  }

  @Test
  void test_handleInsufficientStockException() {
    final InsufficientStockException exception = new InsufficientStockException("Insufficient stock!");

    final ResponseEntity<ErrorResponse> responseEntity = warehouseControllerAdvice.handleInsufficientStockException(exception, webRequest);

    assertThat(responseEntity, is(notNullValue()));
    assertThat(responseEntity.getStatusCode().value(), is(HttpStatus.UNPROCESSABLE_ENTITY.value()));
    assertThat(responseEntity.getBody(), is(notNullValue()));
    assertThat(responseEntity.getBody().getErrorCode(), is("W-422a"));
    assertThat(responseEntity.getBody().getErrorMessage(), is(notNullValue()));
    assertThat(responseEntity.getBody().getMessages(), is(notNullValue()));
    assertThat(responseEntity.getBody().getTimestamp(), is(NOW));
  }

  @Test
  void test_handleAllExceptions() {
    final Exception exception = new RuntimeException("Exception!");

    final ResponseEntity<ErrorResponse> responseEntity = warehouseControllerAdvice.handleAllExceptions(exception, webRequest);

    assertThat(responseEntity, is(notNullValue()));
    assertThat(responseEntity.getStatusCode().value(), is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    assertThat(responseEntity.getBody(), is(notNullValue()));
    assertThat(responseEntity.getBody().getErrorCode(), is("W-500a"));
    assertThat(responseEntity.getBody().getErrorMessage(), is(notNullValue()));
    assertThat(responseEntity.getBody().getMessages(), is(notNullValue()));
    assertThat(responseEntity.getBody().getTimestamp(), is(NOW));
  }
}