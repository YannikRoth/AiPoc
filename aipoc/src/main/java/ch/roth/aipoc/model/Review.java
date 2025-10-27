package ch.roth.aipoc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @JsonProperty
    private String reviewText;
    @JsonProperty
    private ReviewCategorization reviewCategorization;
    @JsonProperty
    private String reviewCategoryExplanation;
    @JsonProperty
    private boolean isAllowedForAutoPublish;
    @JsonProperty
    private String reviewResponseIdea;
    @JsonProperty
    private Locale locale;

}
