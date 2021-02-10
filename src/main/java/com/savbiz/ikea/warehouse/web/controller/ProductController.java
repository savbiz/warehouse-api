package com.savbiz.ikea.warehouse.web.controller;

import com.savbiz.ikea.warehouse.service.ProductService;
import com.savbiz.ikea.warehouse.service.file.FileService;
import com.savbiz.ikea.warehouse.web.dto.request.ProductFileRequest;
import com.savbiz.ikea.warehouse.web.dto.response.ProductResponse;
import com.savbiz.ikea.warehouse.web.dto.response.SimpleProductResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@AllArgsConstructor
@RequestMapping(path = {"/api/v1/products"}, produces = APPLICATION_JSON_VALUE)
@RestController
@Validated
public class ProductController {

  private final ProductService productService;
  private final FileService<ProductFileRequest> fileService;

  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(produces = APPLICATION_JSON_VALUE)
  public List<SimpleProductResponse> getProducts() {
    return productService.getProducts();
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> sellProduct(@PathVariable Long id) {
    productService.sellProduct(id);
    return ResponseEntity.ok().build();
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public List<ProductResponse> uploadProducts(@RequestParam("file") MultipartFile file) throws IOException {
    final ProductFileRequest productFileRequest = fileService.parse(file);
    return productService.saveProducts(productFileRequest);
  }
}
