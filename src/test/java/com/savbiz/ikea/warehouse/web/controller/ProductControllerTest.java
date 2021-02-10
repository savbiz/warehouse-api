package com.savbiz.ikea.warehouse.web.controller;

import com.savbiz.ikea.warehouse.config.WarehouseConfiguration;
import com.savbiz.ikea.warehouse.service.ProductService;
import com.savbiz.ikea.warehouse.service.file.FileService;
import com.savbiz.ikea.warehouse.service.utils.UTCNowSupplier;
import com.savbiz.ikea.warehouse.web.dto.request.ProductFileRequest;
import com.savbiz.ikea.warehouse.web.dto.response.ProductResponse;
import com.savbiz.ikea.warehouse.web.dto.response.SimpleProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static test_utils.ResourceLoader.getResource;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProductController.class)
@Import(WarehouseConfiguration.class)
class ProductControllerTest {

  @MockBean
  private ProductService productService;

  @MockBean
  private FileService<ProductFileRequest> fileService;

  @Autowired
  private MockMvc mockMvc;

  private static final String BASE_PATH = "/api/v1/products/";

  @BeforeEach
  void setMockMvc() {
    this.mockMvc = standaloneSetup(new ProductController(productService, fileService))
        .setControllerAdvice(new WarehouseControllerAdvice(mock(UTCNowSupplier.class)))
        .build();
  }

  @Test
  void test_getProducts() throws Exception {
    when(productService.getProducts()).thenReturn(List.of(newSimpleProductResponse(), newSimpleProductResponse()));

    mockMvc.perform(get(BASE_PATH)
        .contentType(APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(status().isOk());

    verify(productService).getProducts();
  }

  @Test
  void test_sellProduct() throws Exception {
    final Long id = 1L;

    mockMvc.perform(delete(BASE_PATH + id))
        .andExpect(status().isOk());

    verify(productService).sellProduct(id);
  }

  @Test
  void test_uploadProducts() throws Exception {
    final MockMultipartFile file = new MockMultipartFile("file", "products.json", MediaType.MULTIPART_FORM_DATA_VALUE, getResource("/json/products.json").getBytes());

    when(fileService.parse(any(MultipartFile.class))).thenReturn(ProductFileRequest.builder().build());
    when(productService.saveProducts(any(ProductFileRequest.class))).thenReturn(List.of(newProductResponse()));

    mockMvc.perform(multipart(BASE_PATH + "upload/")
        .file(file)
        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
        .andExpect(status().isCreated());

    verify(fileService).parse(any(MultipartFile.class));
    verify(productService).saveProducts(any(ProductFileRequest.class));
  }

  private ProductResponse newProductResponse() {
    final ProductResponse.ProductArticle productArticle = ProductResponse.ProductArticle.builder()
        .id(1L)
        .amount(10)
        .build();

    return ProductResponse.builder()
        .id(1L)
        .name("product_1")
        .price(BigDecimal.TEN)
        .currency("USD")
        .productArticles(List.of(productArticle))
        .build();
  }

  private SimpleProductResponse newSimpleProductResponse() {
    return SimpleProductResponse.builder()
        .id(1L)
        .name("product_1")
        .price(BigDecimal.TEN)
        .currency("USD")
        .availableQuantity(4)
        .build();
  }
}
