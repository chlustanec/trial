package cz.ondrabilek.codeassignment.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static cz.ondrabilek.codeassignment.config.Constants.*;

public class SentenceUtils {

    public static String getMatchesRegex() {
        return "^.*?" + getSplitRegex() + ".*";
    }

    public static String getSplitRegex() {
        return "(?<!" + StringUtils.join(SENTENCE_DIVIDERS_EXCEPTIONS, "|") + ")[" + StringUtils.join(SENTENCE_DIVIDERS, "|") + "]";
    }

    public static List<String> splitToWordsAndSort(String sentenceStr) {
        return Arrays.stream(splitWords(sentenceStr))
                .filter(w -> !StringUtils.isEmpty(w))
                .sorted(String::compareToIgnoreCase)
                .collect(Collectors.toList());
    }

    private static String[] splitWords(String sentenceStr) {
        return StringUtils.split(sentenceStr, WORD_DIVIDERS);
    }

}
