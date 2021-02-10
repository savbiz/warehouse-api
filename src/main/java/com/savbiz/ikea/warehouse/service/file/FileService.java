package com.savbiz.ikea.warehouse.service.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService<T> {

  T parse(MultipartFile file) throws IOException;

}
