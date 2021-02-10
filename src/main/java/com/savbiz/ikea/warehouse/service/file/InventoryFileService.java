package com.savbiz.ikea.warehouse.service.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.savbiz.ikea.warehouse.web.dto.request.InventoryFileRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class InventoryFileService implements FileService<InventoryFileRequest> {

  private final ObjectMapper objectMapper;

  @Override
  public InventoryFileRequest parse(MultipartFile file) throws IOException {
    return objectMapper.readValue(file.getBytes(), InventoryFileRequest.class);
  }
}
