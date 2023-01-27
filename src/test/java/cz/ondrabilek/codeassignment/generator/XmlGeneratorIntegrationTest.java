package cz.ondrabilek.codeassignment.generator;


import cz.ondrabilek.codeassignment.FileTest;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
@Ignore
public class XmlGeneratorIntegrationTest extends FileTest {

    @Autowired
    private XmlGenerator generator;

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

    @Test
    @SneakyThrows
    @Ignore
    void large() {
        log.info("Heap max size: {}", Runtime.getRuntime().maxMemory());
        generator.parseAndOutput(getLargeFile().toString());
    }

}