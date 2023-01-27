package cz.ondrabilek.codeassignment.generator;

import cz.ondrabilek.codeassignment.FileTest;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
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
    @Ignore
    void small() {
        generator.parseAndOutput(getSmallFile().toString());
    }

}