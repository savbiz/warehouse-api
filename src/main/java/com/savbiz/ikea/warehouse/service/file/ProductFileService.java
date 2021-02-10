package com.savbiz.ikea.warehouse.service.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.savbiz.ikea.warehouse.web.dto.request.ProductFileRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class ProductFileService implements FileService<ProductFileRequest> {

  private final ObjectMapper objectMapper;

  @Override
  public ProductFileRequest parse(MultipartFile file) throws IOException {
    return objectMapper.readValue(file.getBytes(), ProductFileRequest.class);
  }
}
