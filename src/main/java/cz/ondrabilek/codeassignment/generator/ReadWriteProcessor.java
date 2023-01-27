package cz.ondrabilek.codeassignment.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.istack.internal.NotNull;
import cz.ondrabilek.codeassignment.model.Sentence;
import cz.ondrabilek.codeassignment.model.SentenceFactory;
import cz.ondrabilek.codeassignment.utils.SentenceUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Slf4j
@Component
class ReadWriteProcessor {

    public int readParseAndOutput(@NotNull InputStream inputStream, @NotNull OutputStream outputStream, @NotNull ObjectMapper objectMapper) throws IOException {
        Objects.requireNonNull(inputStream, "inputStream is NULL");
        Objects.requireNonNull(outputStream, "outputStream is NULL");
        Objects.requireNonNull(objectMapper, "objectMapper is NULL");

        long sentenceCounter = 0;
        int maxWords = 0;

        String matchesRegex = SentenceUtils.getMatchesRegex();
        String splitRegex = SentenceUtils.getSplitRegex();

        String restOfLine = "";

        try (BufferedReader li = new BufferedReader(new InputStreamReader(inputStream))) {
            for (String line = li.readLine(); line != null; line = li.readLine()) {
                // skipping blank line
                if (StringUtils.isBlank(line)) {
                    continue;
                }

                List<String> sentences = new ArrayList<>();

                // Joining by space, I consider newline as word separator
                // and this prevents two words to be concatenated
                String lineToSplit = restOfLine + " " + line;

                while (lineToSplit.matches(matchesRegex)) {
                    String[] split = lineToSplit.split(splitRegex, 2);

                    sentences.add(split[0].trim());
                    lineToSplit = split[1];
                }

                restOfLine = lineToSplit;

                for (String sentenceString : sentences) {
                    // Skipping empty sentences. This can happen if multiple sentence dividers are adjacent
                    // or separated only by whitespace chars.
                    if (StringUtils.isEmpty(sentenceString)) {
                        continue;
                    }

                    Sentence sentence = getSentence(sentenceString, ++sentenceCounter);

                    maxWords = Math.max(maxWords, sentence.getNumberOfWords());
                    outputStream.write(objectMapper.writeValueAsBytes(sentence));
                }
            }
        }

        logRemainingNotSentence(restOfLine);

        return maxWords;
    }

    private void logRemainingNotSentence(String restOfLine) {
        if (!StringUtils.isEmpty(restOfLine)) {
            log.warn("There is Not-sentence left: {}", restOfLine);
        }
    }

    private Sentence getSentence(String sentenceString, long orderOfSentence) {
        Sentence sentence = SentenceFactory.create(sentenceString);
        sentence.setOrder(orderOfSentence);
        return sentence;
    }

}
