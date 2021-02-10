package com.savbiz.ikea.warehouse.exception;

import java.util.function.Supplier;

public class InsufficientStockException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public InsufficientStockException(String message) {
    super(message);
  }

  public static Supplier<InsufficientStockException> supplier(String message) {
    return () -> new InsufficientStockException(message);
  }
}
