package cz.ondrabilek.codeassignment.objectmapper;

import cz.ondrabilek.codeassignment.model.Sentence;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvObjectMapperTest {

    private final CsvObjectMapper om = new CsvObjectMapper();

    @Test
    @SneakyThrows
    void happyPath_string() {
        Sentence sentence = new Sentence();
        sentence.setWords(Arrays.asList("I", "Predict", "A", "Riot"));
        sentence.setOrder(1L);

        assertEquals(
                "Sentence 1, I, Predict, A, Riot" + System.lineSeparator(),
                om.writeValueAsString(sentence)
        );
    }

    @Test
    @SneakyThrows
    void happyPath_bytes() {
        Sentence sentence = new Sentence();
        sentence.setWords(Arrays.asList("I", "Predict", "A", "Riot"));
        sentence.setOrder(1L);

        assertArrayEquals(
                ("Sentence 1, I, Predict, A, Riot" + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                om.writeValueAsBytes(sentence)
        );
    }

    @Test
    @SneakyThrows
    void orderNotSet() {
        Sentence sentence = new Sentence();
        sentence.setWords(Arrays.asList("I", "Predict", "A", "Riot"));

        assertEquals(
                "Sentence null, I, Predict, A, Riot" + System.lineSeparator(),
                om.writeValueAsString(sentence)
        );
    }
}