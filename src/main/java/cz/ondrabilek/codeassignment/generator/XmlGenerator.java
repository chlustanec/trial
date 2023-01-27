package cz.ondrabilek.codeassignment.generator;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class XmlGenerator extends AbstractGenerator {

    private final ReadWriteProcessor processor;

    @Override
    public Path parseAndOutput(@NotNull String inputFilePath) throws IOException {
        Objects.requireNonNull(inputFilePath, "inputFilePath is NULL");

        File inputFile = FileUtils.getFile(inputFilePath);
        checkFileExists(inputFilePath, inputFile);

        Path outputFilePath = getWriteableOutputFilePath(inputFile);

        try (OutputStream xmlOut = Files.newOutputStream(outputFilePath)) {
            log.info("Output file created: {}", outputFilePath);
            writeToOutput(inputFile, xmlOut);
        }

        return outputFilePath;
    }

    private void writeToOutput(File inputFile, OutputStream xmlOut) throws IOException {
        insertToBeginningOfTheFile(xmlOut);
        processor.readParseAndOutput(FileUtils.openInputStream(inputFile), xmlOut, new XmlMapper());
        insertToEndOfTheFile(xmlOut);
    }

    protected String getExtension() {
        return ".xml";
    }

    protected void insertToBeginningOfTheFile(OutputStream xmlOut) throws IOException {
        xmlOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><text>".getBytes(StandardCharsets.UTF_8));
    }

    protected void insertToEndOfTheFile(OutputStream xmlOut) throws IOException {
        xmlOut.write("</text>".getBytes(StandardCharsets.UTF_8));
    }



}
