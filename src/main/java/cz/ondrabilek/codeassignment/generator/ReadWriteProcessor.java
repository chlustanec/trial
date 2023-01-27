package cz.ondrabilek.codeassignment.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.ondrabilek.codeassignment.model.Sentence;
import cz.ondrabilek.codeassignment.model.SentenceFactory;
import cz.ondrabilek.codeassignment.utils.SentenceUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


@Slf4j
@Component
class ReadWriteProcessor {

    private static final int BUFFER_SIZE = 1024;
    private final String splitRegex = SentenceUtils.getSplitRegex();
    private final String sentenceDividersMatcher = SentenceUtils.getMatchesRegex();

    public int readParseAndOutput(@NotNull InputStream inputStream, @NotNull OutputStream outputStream, @NotNull ObjectMapper objectMapper) throws IOException {
        long sentenceCounter = 0;
        int maxWords = 0;

        StringBuilder restOfLine = new StringBuilder();
        try (InputStreamReader is = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            char[] readChars = new char[BUFFER_SIZE];
            int noOfReadChars = BUFFER_SIZE;

            while (noOfReadChars == BUFFER_SIZE) {
                noOfReadChars = is.read(readChars);
                String chunk = new String(readChars);

                if (noOfReadChars < BUFFER_SIZE) {
                    chunk = StringUtils.left(chunk, noOfReadChars);
                }

                chunk = StringUtils.replaceChars(chunk, "\r\n", " ");
                restOfLine.append(chunk);

                while (restOfLine.toString().matches(sentenceDividersMatcher)) {
                    String[] split = restOfLine.toString().split(splitRegex, 2);

                    if (StringUtils.isNotEmpty(split[0])) {
                        Sentence sentence = getSentence(split[0].trim(), ++sentenceCounter);
                        maxWords = Math.max(maxWords, sentence.getNumberOfWords());
                        outputStream.write(objectMapper.writeValueAsBytes(sentence));
                    }

                    restOfLine = new StringBuilder(split[1]);
                }
            }
        }

        logRemainingNotSentence(restOfLine.toString());

        return maxWords;
    }

    private void logRemainingNotSentence(String restOfLine) {
        if (StringUtils.isNotBlank(restOfLine)) {
            log.warn("There is Not-sentence left: {}", restOfLine);
        }
    }

    private Sentence getSentence(String sentenceString, long orderOfSentence) {
        Sentence sentence = SentenceFactory.create(sentenceString);
        sentence.setOrder(orderOfSentence);
        return sentence;
    }

}
