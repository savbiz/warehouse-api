package com.savbiz.ikea.warehouse.web.controller;

import com.savbiz.ikea.warehouse.exception.ArticleNotFoundException;
import com.savbiz.ikea.warehouse.exception.InsufficientStockException;
import com.savbiz.ikea.warehouse.exception.ProductNotFoundException;
import com.savbiz.ikea.warehouse.service.utils.UTCNowSupplier;
import com.savbiz.ikea.warehouse.web.dto.error.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@AllArgsConstructor
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class WarehouseControllerAdvice {

  private final UTCNowSupplier utcNowSupplier;

  private static final String STRING_ERROR = "Error occurred with message: [{}]";

  @ExceptionHandler({ArticleNotFoundException.class})
  public ResponseEntity<ErrorResponse> handleArticleNotFoundException(final ArticleNotFoundException exception, WebRequest request) {
    log.error(STRING_ERROR, exception.getMessage(), exception);

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ErrorResponse.builder()
            .errorCode("W-400a")
            .errorMessage(exception.getMessage())
            .messages(List.of(request.getDescription(false)))
            .timestamp(utcNowSupplier.get())
            .build());
  }

  @ExceptionHandler({ProductNotFoundException.class})
  public ResponseEntity<ErrorResponse> handleProductNotFoundException(final ProductNotFoundException exception, WebRequest request) {
    log.error(STRING_ERROR, exception.getMessage(), exception);

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ErrorResponse.builder()
            .errorCode("W-400b")
            .errorMessage(exception.getMessage())
            .messages(List.of(request.getDescription(false)))
            .timestamp(utcNowSupplier.get())
            .build());
  }

  @ExceptionHandler({InsufficientStockException.class})
  public ResponseEntity<ErrorResponse> handleInsufficientStockException(final InsufficientStockException exception, WebRequest request) {
    log.error(STRING_ERROR, exception.getMessage(), exception);

    return ResponseEntity
        .status(HttpStatus.UNPROCESSABLE_ENTITY)
        .body(ErrorResponse.builder()
            .errorCode("W-422a")
            .errorMessage(exception.getMessage())
            .messages(List.of(request.getDescription(false)))
            .timestamp(utcNowSupplier.get())
            .build());
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception exception, WebRequest request) {
    log.error(STRING_ERROR, exception.getMessage(), exception);

    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ErrorResponse.builder()
            .errorCode("W-500a")
            .errorMessage(exception.getMessage())
            .messages(List.of(request.getDescription(false)))
            .timestamp(utcNowSupplier.get())
            .build());
  }

}
