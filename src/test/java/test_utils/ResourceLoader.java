package test_utils;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResourceLoader {
  public static String getResource(String path) {
    try {
      return Files.readString(Paths.get(new ClassPathResource(path).getURI()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
