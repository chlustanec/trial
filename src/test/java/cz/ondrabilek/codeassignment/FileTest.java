package cz.ondrabilek.codeassignment;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public abstract class FileTest {

    protected Path getPath(String path) throws IOException {
        return new ClassPathResource(path).getFile().toPath();
    }

    protected Path getTinyFile() throws IOException {
        return getPath("files/tiny.in");
    }

    protected Path getHugeOneLineFile() throws IOException {
        return getPath("files/large_oneline.in");
    }

    protected Path getSmallFile() throws IOException {
        return getPath("files/small.in");
    }

    protected void deleteAllCreatedFiles() throws IOException {
        File[] files = getSmallFile().getParent().toFile().listFiles((dir, name) -> !name.toLowerCase(Locale.ROOT).endsWith(".in"));
        for (File file : files) {
            Files.deleteIfExists(file.toPath());
        }
    }

}
