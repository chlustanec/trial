package cz.ondrabilek.codeassignment.generator;

import cz.ondrabilek.codeassignment.FileTest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
@Disabled
class CsvGeneratorIntegrationTest extends FileTest {

    @Autowired
    private CsvGenerator generator;

    @AfterEach
    @SneakyThrows
    void tearDown() {
        deleteAllCreatedFiles();
    }

    @Test
    @SneakyThrows
    void happyPath_hugeOneLine() {
        log.info("Heap size: {}", Runtime.getRuntime().maxMemory());
        generator.parseAndOutput(getHugeOneLineFile().toString());
    }

    @Test
    @SneakyThrows
    void small() {
        log.info("Heap size: {}", Runtime.getRuntime().maxMemory());
        generator.parseAndOutput(getSmallFile().toString());
    }

}