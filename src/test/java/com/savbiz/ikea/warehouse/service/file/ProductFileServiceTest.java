package com.savbiz.ikea.warehouse.service.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.savbiz.ikea.warehouse.web.dto.request.ProductFileRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static test_utils.ResourceLoader.getResource;

@ExtendWith(SpringExtension.class)
class ProductFileServiceTest {

  private ProductFileService productFileService = new ProductFileService(new ObjectMapper());

  @Test
  void test_parse() throws IOException {
    final MockMultipartFile file = new MockMultipartFile("file", "products.json", MediaType.MULTIPART_FORM_DATA_VALUE, getResource("/json/products.json").getBytes());

    final ProductFileRequest productFileRequest = productFileService.parse(file);

    assertThat(productFileRequest.getProducts(), hasSize(2));
    final ProductFileRequest.ProductDto productDto1 = productFileRequest.getProducts().get(0);
    final ProductFileRequest.ProductDto productDto2 = productFileRequest.getProducts().get(1);

    assertThat(productDto1.getName(), is("Dining Chair"));
    assertThat(productDto1.getContainArticles(), hasSize(3));
    assertThat(productDto1.getContainArticles().get(0).getArticleId(), is(1L));
    assertThat(productDto1.getContainArticles().get(0).getAmountOf(), is(4));

    assertThat(productDto1.getContainArticles().get(1).getArticleId(), is(2L));
    assertThat(productDto1.getContainArticles().get(1).getAmountOf(), is(8));

    assertThat(productDto1.getContainArticles().get(2).getArticleId(), is(3L));
    assertThat(productDto1.getContainArticles().get(2).getAmountOf(), is(1));

    assertThat(productDto2.getName(), is("Dinning Table"));
    assertThat(productDto2.getContainArticles(), hasSize(3));
    assertThat(productDto2.getContainArticles().get(0).getArticleId(), is(1L));
    assertThat(productDto2.getContainArticles().get(0).getAmountOf(), is(4));

    assertThat(productDto2.getContainArticles().get(1).getArticleId(), is(2L));
    assertThat(productDto2.getContainArticles().get(1).getAmountOf(), is(8));

    assertThat(productDto2.getContainArticles().get(2).getArticleId(), is(4L));
    assertThat(productDto2.getContainArticles().get(2).getAmountOf(), is(1));
  }

}
