package cz.ondrabilek.codeassignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

import java.util.List;
import java.util.Objects;

@JacksonXmlRootElement(localName = "sentence")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Sentence {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "word")
    private List<String> words;

    @JsonIgnore
    private Long order;

    public Sentence(List<String> words) {
        this.words = words;
    }

    @JsonIgnore
    public int getNumberOfWords() {
        return Objects.nonNull(words) ? words.size() : 0;
    }
}
