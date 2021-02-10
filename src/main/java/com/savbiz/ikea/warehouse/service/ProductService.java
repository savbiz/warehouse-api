package com.savbiz.ikea.warehouse.service;

import com.savbiz.ikea.warehouse.web.dto.request.ProductFileRequest;
import com.savbiz.ikea.warehouse.web.dto.response.ProductResponse;
import com.savbiz.ikea.warehouse.web.dto.response.SimpleProductResponse;

import java.util.List;

public interface ProductService {

  List<ProductResponse> saveProducts(ProductFileRequest productFileRequest);

  List<SimpleProductResponse> getProducts();

  void sellProduct(Long id);
}
