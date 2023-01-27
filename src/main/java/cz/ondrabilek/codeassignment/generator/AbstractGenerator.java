package cz.ondrabilek.codeassignment.generator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Slf4j
abstract class AbstractGenerator {

    abstract Path parseAndOutput(String inputFilePath) throws IOException;

    protected void checkFileExists(String filePath, File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException("File " + filePath + " not found");
        }
    }

    protected String getInputFileNameWoutExtension(File inputFile) {
        return FilenameUtils.removeExtension(inputFile.toPath().getFileName().toString());
    }

    protected Path getOutputFilePath(File inputFile) {
        return inputFile.getParentFile().toPath().resolve(getInputFileNameWoutExtension(inputFile) + "_" + Timestamp.valueOf(LocalDateTime.now()).toInstant().toEpochMilli() +  getExtension());
    }

    protected Path getTempOutputFilePath(File inputFile) throws IOException {
        return Files.createTempFile(getInputFileNameWoutExtension(inputFile) + "_", getExtension());
    }

    protected Path getWriteableOutputFilePath(File inputFile) throws IOException {
        return inputFile.getParentFile().canWrite() ?
                getOutputFilePath(inputFile) :
                getTempOutputFilePath(inputFile);
    }

    protected abstract String getExtension();

}
