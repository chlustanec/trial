package cz.ondrabilek.codeassignment.generator;

import cz.ondrabilek.codeassignment.FileTest;
import cz.ondrabilek.codeassignment.objectmapper.CsvObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReadWriteProcessorTest extends FileTest {

    public static final String LS = System.lineSeparator();
    private final ReadWriteProcessor processor = new ReadWriteProcessor();
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final CsvObjectMapper om = new CsvObjectMapper();


    @Test
    @SneakyThrows
    void happyPath() {
        int maxWords = processor.readParseAndOutput(Files.newInputStream(getTinyFile()), outputStream, om);

        assertEquals("Sentence 1, he, shocking, shouted, was, What, 你这肮脏的掠夺者, 停在那儿" + LS +
                        "Sentence 2, a, because, Chinese, couldn't, I, isn't, mother, my, perhaps, tongue, understand, word" + LS,
                outputStream.toString("UTF-8"));

        assertEquals(12, maxWords);
    }

    @Test
    @SneakyThrows
    void nullParameters() {
        assertThrows(NullPointerException.class,
                () -> processor.readParseAndOutput(null, new ByteArrayOutputStream(), new CsvObjectMapper()));
        assertThrows(NullPointerException.class,
                () -> processor.readParseAndOutput(Files.newInputStream(getTinyFile()), null, new CsvObjectMapper()));
        assertThrows(NullPointerException.class,
                () -> processor.readParseAndOutput(Files.newInputStream(getTinyFile()), new ByteArrayOutputStream(), null));
    }

    @Test
    @SneakyThrows
    void incompleteSentenceIsNotUSed() {
        InputStream is = new ByteArrayInputStream("Sentence one. unfinished sentence".getBytes(StandardCharsets.UTF_8));
        processor.readParseAndOutput(is, outputStream, new CsvObjectMapper());
        assertEquals("Sentence 1, one, Sentence" + LS, outputStream.toString());
    }

    @Test
    @SneakyThrows
    void sentenceDividersWork() {
        InputStream is = new ByteArrayInputStream("Sentence one. unfinished sentence".getBytes(StandardCharsets.UTF_8));
        processor.readParseAndOutput(is, outputStream, om);
        assertEquals("Sentence 1, one, Sentence" + LS, outputStream.toString());
        outputStream.reset();

        is = new ByteArrayInputStream("Sentence one? unfinished sentence".getBytes(StandardCharsets.UTF_8));
        processor.readParseAndOutput(is, outputStream, om);
        assertEquals("Sentence 1, one, Sentence" + LS, outputStream.toString());
        outputStream.reset();

        is = new ByteArrayInputStream("Sentence one! unfinished sentence".getBytes(StandardCharsets.UTF_8));
        processor.readParseAndOutput(is, outputStream, om);
        assertEquals("Sentence 1, one, Sentence" + LS, outputStream.toString());
    }

    @Test
    @SneakyThrows
    void adjacentSentenceDividersAreOk() {
        InputStream is = new ByteArrayInputStream("Sentence one..!?. unfinished sentence".getBytes(StandardCharsets.UTF_8));
        processor.readParseAndOutput(is, outputStream, om);
        assertEquals("Sentence 1, one, Sentence" + LS, outputStream.toString());
    }
}