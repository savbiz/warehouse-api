package com.savbiz.ikea.warehouse.web.dto.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;
import java.util.List;

@Value
@Builder
public class ErrorResponse {

  String errorCode;
  String errorMessage;
  List<String> messages;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEE MMM dd HH:mm:ss Z yyyy")
  ZonedDateTime timestamp;

}
