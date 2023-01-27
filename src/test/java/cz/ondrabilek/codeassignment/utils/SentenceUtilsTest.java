package cz.ondrabilek.codeassignment.utils;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SentenceUtilsTest {

    @Test
    @SneakyThrows
    void splitToWordsAndSort_happyPath() {
        List<String> words = SentenceUtils.splitToWordsAndSort("hi hello");
        assertEquals(Arrays.asList("hello", "hi"), words);
    }

    @Test
    @SneakyThrows
    void splitToWordsAndSort_adjacentDividers() {
        List<String> words = SentenceUtils.splitToWordsAndSort("\thi  ,;  (hello)");
        assertEquals(Arrays.asList("hello", "hi"), words);
    }

    @Test
    @SneakyThrows
    void splitToWordsAndSort_wordDelimiters() {
        assertEquals(Arrays.asList("hello", "hi"), SentenceUtils.splitToWordsAndSort("hi\thello"));
        assertEquals(Arrays.asList("hello", "hi"), SentenceUtils.splitToWordsAndSort("hi hello"));
        assertEquals(Arrays.asList("hello", "hi"), SentenceUtils.splitToWordsAndSort("hi-hello"));
        assertEquals(Arrays.asList("hello", "hi"), SentenceUtils.splitToWordsAndSort("hi,hello"));
        assertEquals(Arrays.asList("hello", "hi"), SentenceUtils.splitToWordsAndSort("hi;hello"));
        assertEquals(Arrays.asList("hello", "hi"), SentenceUtils.splitToWordsAndSort("hi:hello"));
        assertEquals(Arrays.asList("hello", "hi"), SentenceUtils.splitToWordsAndSort("hi(hello"));
        assertEquals(Arrays.asList("hello", "hi"), SentenceUtils.splitToWordsAndSort("hi)hello"));
        assertEquals(Arrays.asList("hello", "hi"), SentenceUtils.splitToWordsAndSort("hi{hello"));
        assertEquals(Arrays.asList("hello", "hi"), SentenceUtils.splitToWordsAndSort("hi}hello"));
        assertEquals(Arrays.asList("hello", "hi"), SentenceUtils.splitToWordsAndSort("hi[hello"));
        assertEquals(Arrays.asList("hello", "hi"), SentenceUtils.splitToWordsAndSort("hi]hello"));
        assertEquals(Arrays.asList("hello", "hi"), SentenceUtils.splitToWordsAndSort("hi<hello"));
        assertEquals(Arrays.asList("hello", "hi"), SentenceUtils.splitToWordsAndSort("hi>hello"));
    }
}