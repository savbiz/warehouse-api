package com.savbiz.ikea.warehouse.web.controller;

import com.savbiz.ikea.warehouse.config.WarehouseConfiguration;
import com.savbiz.ikea.warehouse.entity.Article;
import com.savbiz.ikea.warehouse.mapper.Mapper;
import com.savbiz.ikea.warehouse.service.ArticleService;
import com.savbiz.ikea.warehouse.service.file.FileService;
import com.savbiz.ikea.warehouse.service.utils.UTCNowSupplier;
import com.savbiz.ikea.warehouse.web.dto.request.InventoryFileRequest;
import com.savbiz.ikea.warehouse.web.dto.response.ArticleResponse;
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

import java.util.List;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static test_utils.ResourceLoader.getResource;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ArticleController.class)
@Import(WarehouseConfiguration.class)
class ArticleControllerTest {

  @MockBean
  private ArticleService articleService;

  @MockBean
  private Mapper mapper;

  @MockBean
  private FileService<InventoryFileRequest> fileService;

  @Autowired
  private MockMvc mockMvc;

  private static final String BASE_PATH = "/api/v1/articles/";

  @BeforeEach
  void setMockMvc() {
    this.mockMvc = standaloneSetup(new ArticleController(articleService, fileService, mapper))
        .setControllerAdvice(new WarehouseControllerAdvice(mock(UTCNowSupplier.class)))
        .build();
  }

  @Test
  void test_uploadInventory() throws Exception {
    final MockMultipartFile file = new MockMultipartFile("file", "inventory.json", MediaType.MULTIPART_FORM_DATA_VALUE, getResource("/json/inventory.json").getBytes());

    when(fileService.parse(any(MultipartFile.class))).thenReturn(InventoryFileRequest.builder().build());
    when(mapper.toArticleEntities(any(InventoryFileRequest.class))).thenReturn(List.of(newArticle(), newArticle()));
    when(articleService.saveArticles(anyList())).then(returnsFirstArg());
    when(mapper.toArticleResponse(any(Article.class))).thenReturn(newArticleResponse());

    mockMvc.perform(multipart(BASE_PATH + "upload/")
        .file(file)
        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
        .andExpect(status().isCreated());

    verify(fileService).parse(any(MultipartFile.class));
    verify(mapper).toArticleEntities(any(InventoryFileRequest.class));
    verify(articleService).saveArticles(anyList());
    verify(mapper, times(2)).toArticleResponse(any(Article.class));
  }

  private Article newArticle() {
    return Article.builder()
        .id(1L)
        .name("article_1")
        .availableStock(4)
        .build();
  }

  private ArticleResponse newArticleResponse() {
    return ArticleResponse.builder()
        .id(1L)
        .name("article_1")
        .availableStock(4)
        .build();
  }
}