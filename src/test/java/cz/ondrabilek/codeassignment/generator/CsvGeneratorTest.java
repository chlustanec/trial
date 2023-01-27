package cz.ondrabilek.codeassignment.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.ondrabilek.codeassignment.FileTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CsvGeneratorTest extends FileTest {
    @Mock
    private ReadWriteProcessor processor;
    @InjectMocks
    private CsvGenerator generator;

    @AfterEach
    @SneakyThrows
    void tearDown() {
        deleteAllCreatedFiles();
    }

    @Test
    @SneakyThrows
    void happyPath() {
        when(processor.readParseAndOutput(any(InputStream.class), any(OutputStream.class), any(ObjectMapper.class))).thenAnswer(invocationOnMock -> {
            OutputStream argument = invocationOnMock.getArgument(1);
            argument.write("Sentence 1, a, b, c, d".getBytes(StandardCharsets.UTF_8));
            return 4;
        });

        Path createdFilePath = generator.parseAndOutput(getSmallFile().toString());
        assertTrue(Files.exists(createdFilePath));
        List<String> lines = Files.readAllLines(createdFilePath);

        assertEquals(2, lines.size());
        assertEquals(", Word 1, Word 2, Word 3, Word 4", lines.get(0));
        assertEquals("Sentence 1, a, b, c, d", lines.get(1));
    }

    @Test
    @SneakyThrows
    void nullFile() {
        assertThrows(NullPointerException.class, () -> generator.parseAndOutput(null), "inputFilePath is NULL");
    }

    @Test
    @SneakyThrows
    void notExistingFile() {
        assertThrows(FileNotFoundException.class, () -> generator.parseAndOutput("Q:/abc/abc.in"), "File Q:/abc/abc.in not found");
    }

}