package com.savbiz.ikea.warehouse.service.utils;

import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.function.Supplier;

@Component
public class UTCNowSupplier implements Supplier<ZonedDateTime> {

  @Override
  public ZonedDateTime get() {
    return ZonedDateTime.now(ZoneId.of("UTC"));
  }

}
