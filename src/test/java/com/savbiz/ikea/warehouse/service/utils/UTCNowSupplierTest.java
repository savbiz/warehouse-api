package com.savbiz.ikea.warehouse.service.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UTCNowSupplierTest {

  @Test
  void get() {
    assertEquals("UTC", new UTCNowSupplier().get().getZone().getId());
  }

}
