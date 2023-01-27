package cz.ondrabilek.codeassignment.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SentenceFactoryTest {

    @Test
    void happyPath() {
        assertEquals(
                new Sentence(Arrays.asList("a", "and", "do", "face", "fix", "go", "I'll", "like", "morning", "my", "pro", "show", "the", "Time", "to")),
                SentenceFactory.create("I'll fix my face and go Time to do the morning show like a pro")
        );
    }

    @Test
    void happyPath_dyslexia() {
        assertEquals(
                new Sentence(Arrays.asList("a", "and", "do", "face", "fix", "go", "I'll", "like", "morning", "my", "pro", "show", "the", "Time", "to")),
                SentenceFactory.create("-I'll    fix ,my face and go, ;Time to do <   ;>the morning show \tlike (a) pro;;")
        );
    }

    @Test
    void emptyString() {
        assertEquals(
                new Sentence(),
                SentenceFactory.create(null)
        );
        assertEquals(
                new Sentence(),
                SentenceFactory.create("")
        );
        assertEquals(
                new Sentence(new ArrayList<>()),
                SentenceFactory.create(" -,")
        );
    }
}