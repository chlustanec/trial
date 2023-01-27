package cz.ondrabilek.codeassignment.config;

import cz.ondrabilek.codeassignment.generator.CsvGenerator;
import cz.ondrabilek.codeassignment.generator.XmlGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CmdLauncher {

    private final ApplicationArguments arguments;
    private final XmlGenerator xmlGenerator;
    private final CsvGenerator csvGenerator;


    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        if (arguments.getSourceArgs().length == 0) {
            log.warn("No file path was passed");
            return;
        }

        String filePath = arguments.getSourceArgs()[0];
        log.info("File path passed: {}", filePath);

        try {
            xmlGenerator.parseAndOutput(filePath);
            csvGenerator.parseAndOutput(filePath);
        } catch (Exception e) {
            log.error("Process ended with error: {}", e.getLocalizedMessage());
        }
    }


}
