package cz.ondrabilek.codeassignment.model;

import cz.ondrabilek.codeassignment.utils.SentenceUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

import java.util.List;

public class SentenceFactory {

    public static Sentence create(@Nullable String sentenceString) {
        if (StringUtils.isEmpty(sentenceString)) {
            return new Sentence();
        }

        List<String> words = SentenceUtils.splitToWordsAndSort(sentenceString);
        return new Sentence(words);
    }
}
