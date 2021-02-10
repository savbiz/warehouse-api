package com.savbiz.ikea.warehouse.web.controller;

import com.savbiz.ikea.warehouse.entity.Article;
import com.savbiz.ikea.warehouse.mapper.Mapper;
import com.savbiz.ikea.warehouse.service.ArticleService;
import com.savbiz.ikea.warehouse.service.file.FileService;
import com.savbiz.ikea.warehouse.web.dto.request.InventoryFileRequest;
import com.savbiz.ikea.warehouse.web.dto.response.ArticleResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@AllArgsConstructor
@RequestMapping(path = {"/api/v1/articles"}, produces = APPLICATION_JSON_VALUE)
@RestController
@Validated
public class ArticleController {

  private final ArticleService articleService;
  private final FileService<InventoryFileRequest> fileService;
  private final Mapper mapper;

  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public List<ArticleResponse> uploadInventory(@RequestParam("file") MultipartFile file) throws IOException {
    final List<Article> articles = mapper.toArticleEntities(fileService.parse(file));
    final List<Article> savedArticles = articleService.saveArticles(articles);

    return savedArticles.stream()
        .map(mapper::toArticleResponse)
        .collect(Collectors.toUnmodifiableList());
  }
}
