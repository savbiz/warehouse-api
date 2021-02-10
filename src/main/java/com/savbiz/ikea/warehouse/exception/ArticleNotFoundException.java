package com.savbiz.ikea.warehouse.exception;

import java.util.function.Supplier;

public class ArticleNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ArticleNotFoundException(String message) {
    super(message);
  }

  public static Supplier<ArticleNotFoundException> supplier(String message) {
    return () -> new ArticleNotFoundException(message);
  }
}
