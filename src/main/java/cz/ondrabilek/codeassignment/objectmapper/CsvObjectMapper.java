package cz.ondrabilek.codeassignment.objectmapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.ondrabilek.codeassignment.model.Sentence;
import org.apache.commons.lang3.NotImplementedException;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class CsvObjectMapper extends ObjectMapper {

    @Override
    public byte[] writeValueAsBytes(Object value)  {
        return writeValueAsString(value).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String writeValueAsString(Object value)  {
        if (Objects.isNull(value)) {
            return "";
        }

        if (!(value instanceof Sentence)) {
            throw new NotImplementedException("Unsuported Object type");
        }

        Sentence sentence = (Sentence) value;

        // If order is NULL, it writes 'null', but it does not crash. Solution would need to be communicated.
        return String.format("Sentence %s, %s%s",
                sentence.getOrder(),
                String.join(", ", sentence.getWords()),
                System.lineSeparator());
    }
}
