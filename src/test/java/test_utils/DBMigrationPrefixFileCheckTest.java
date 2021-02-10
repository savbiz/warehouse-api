package test_utils;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A way to prevent merging SQL migration script with same version.
 */
class DBMigrationPrefixFileCheckTest {

  private static final String DB_MIGRATION_PATH = "src/main/resources/db/migration";
  private static final String FILE_SPLITTER_PATH = "__";

  @Test
  void test_checkUniquePrefixForMigrationScriptVersion() throws IOException {
    final Set<String> uniquePrefix = new HashSet<>();
    final List<String> duplicatePrefixes = new ArrayList<>();

    for (final String filename : listDBMigrationFiles()) {
      final String prefix = StringUtils.substringBefore(filename, FILE_SPLITTER_PATH);

      if (!uniquePrefix.contains(prefix)) {
        uniquePrefix.add(prefix);
      } else {
        duplicatePrefixes.add(prefix);
      }
    }

    assertTrue(duplicatePrefixes.isEmpty());
  }

  private Set<String> listDBMigrationFiles() throws IOException {
    try (final Stream<Path> stream = Files.list(Paths.get(DB_MIGRATION_PATH))) {
      return stream
          .filter(file -> !Files.isDirectory(file))
          .map(Path::getFileName)
          .map(Path::toString)
          .collect(Collectors.toSet());
    }
  }
}
