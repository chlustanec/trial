package cz.ondrabilek.codeassignment.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SentenceTest {

    @Test
    void numberOfWords() {
        assertEquals(0, new Sentence().getNumberOfWords());
        assertEquals(0, new Sentence(new ArrayList<>()).getNumberOfWords());
        assertEquals(1, new Sentence(Arrays.asList("hi")).getNumberOfWords());
    }

}