package cz.ondrabilek.codeassignment.generator;

import cz.ondrabilek.codeassignment.objectmapper.CsvObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CsvGenerator extends AbstractGenerator {

    private final ReadWriteProcessor processor;

    @Override
    public Path parseAndOutput(@NotNull String inputFilePath) throws IOException {
        File inputFile = FileUtils.getFile(inputFilePath);
        checkFileExists(inputFilePath, inputFile);

        Path outputFilePath = getWriteableOutputFilePath(inputFile);
        Path tempOutputFilePath = getTempOutputFilePath(inputFile);

        int maxWords = createFileWithHeaderlessData(inputFile, tempOutputFilePath);

        prependHeader(outputFilePath, tempOutputFilePath, maxWords);

        Files.delete(tempOutputFilePath);

        return outputFilePath;
    }

    private int createFileWithHeaderlessData(File inputFile, Path tempOutputFilePath) throws IOException {
        try (OutputStream xmlOut = Files.newOutputStream(tempOutputFilePath)) {
            return processor.readParseAndOutput(FileUtils.openInputStream(inputFile), xmlOut, new CsvObjectMapper());
        } catch (Exception e) {
            Files.delete(tempOutputFilePath);
            throw e;
        }
    }

    private void prependHeader(Path finalFileOutputPath, Path tempDataFilePath, int maxWords) throws IOException {
        try (OutputStream csvOut = Files.newOutputStream(finalFileOutputPath);
             InputStream tmpCsvIn = Files.newInputStream(tempDataFilePath)) {
            log.info("Output file created: {}", finalFileOutputPath);

            List<String> header = new ArrayList<>();

            for (int i = 1; i <= maxWords; i++) {
                header.add(("Word " + i));
            }

            String headerString = ", " + String.join(", ", header);
            csvOut.write(headerString.getBytes(StandardCharsets.UTF_8));

            csvOut.write(System.lineSeparator().getBytes(StandardCharsets.UTF_8));
            IOUtils.copy(tmpCsvIn, csvOut);
        }
    }

    @Override
    protected String getExtension() {
        return ".csv";
    }

}
