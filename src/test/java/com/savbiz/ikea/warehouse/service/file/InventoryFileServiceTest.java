package com.savbiz.ikea.warehouse.service.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.savbiz.ikea.warehouse.web.dto.request.InventoryFileRequest;
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
class InventoryFileServiceTest {

  private InventoryFileService inventoryFileService = new InventoryFileService(new ObjectMapper());

  @Test
  void test_parse() throws IOException {
    final MockMultipartFile file = new MockMultipartFile("file", "inventory.json", MediaType.MULTIPART_FORM_DATA_VALUE, getResource("/json/inventory.json").getBytes());

    final InventoryFileRequest inventoryFileRequest = inventoryFileService.parse(file);

    assertThat(inventoryFileRequest.getInventory(), hasSize(4));
    final InventoryFileRequest.ArticleDto articleDto1 = inventoryFileRequest.getInventory().get(0);
    final InventoryFileRequest.ArticleDto articleDto2 = inventoryFileRequest.getInventory().get(1);
    final InventoryFileRequest.ArticleDto articleDto3 = inventoryFileRequest.getInventory().get(2);
    final InventoryFileRequest.ArticleDto articleDto4 = inventoryFileRequest.getInventory().get(3);

    assertThat(articleDto1.getId(), is(1L));
    assertThat(articleDto1.getName(), is("leg"));
    assertThat(articleDto1.getStock(), is(12));

    assertThat(articleDto2.getId(), is(2L));
    assertThat(articleDto2.getName(), is("screw"));
    assertThat(articleDto2.getStock(), is(17));

    assertThat(articleDto3.getId(), is(3L));
    assertThat(articleDto3.getName(), is("seat"));
    assertThat(articleDto3.getStock(), is(2));

    assertThat(articleDto4.getId(), is(4L));
    assertThat(articleDto4.getName(), is("table top"));
    assertThat(articleDto4.getStock(), is(1));
  }

}
