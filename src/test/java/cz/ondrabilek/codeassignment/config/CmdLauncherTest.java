package cz.ondrabilek.codeassignment.config;

import cz.ondrabilek.codeassignment.generator.CsvGenerator;
import cz.ondrabilek.codeassignment.generator.XmlGenerator;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.ApplicationArguments;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CmdLauncherTest {

    @Mock
    private ApplicationArguments arguments;
    @Mock
    private XmlGenerator xmlGenerator;
    @Mock
    private CsvGenerator csvGenerator;
    @InjectMocks
    private CmdLauncher cmdLauncher;

    @Captor
    private ArgumentCaptor<String> strCaptor;

    @Test
    @SneakyThrows
    void happyPath() {
        when(arguments.getSourceArgs()).thenReturn(new String[]{"filePath"});

        cmdLauncher.run();

        verify(xmlGenerator, times(1)).parseAndOutput(strCaptor.capture());
        verify(csvGenerator, times(1)).parseAndOutput(strCaptor.capture());
        assertTrue(strCaptor.getAllValues().stream().allMatch(v -> StringUtils.equals("filePath", v)));
    }

    @Test
    @SneakyThrows
    void noArgumentGiven() {
        when(arguments.getSourceArgs()).thenReturn(new String[0]);

        cmdLauncher.run();

        verify(xmlGenerator, never()).parseAndOutput(any());
        verify(csvGenerator, never()).parseAndOutput(any());
    }

    @Test
    @SneakyThrows
    void appDoesNotCrash() {
        when(arguments.getSourceArgs()).thenReturn(new String[]{"filePath"});
        when(xmlGenerator.parseAndOutput(any())).thenThrow(new NullPointerException("Oh no!"));

        cmdLauncher.run();
    }
}