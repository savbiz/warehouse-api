package com.savbiz.ikea.warehouse.exception;

import java.util.function.Supplier;

public class ProductNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ProductNotFoundException(String message) {
    super(message);
  }

  public static Supplier<ProductNotFoundException> supplier(String message) {
    return () -> new ProductNotFoundException(message);
  }
}
