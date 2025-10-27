package ch.roth.aipoc.service;

import ch.roth.aipoc.model.Review;
import ch.roth.aipoc.model.ReviewCategorization;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiApi.ChatCompletionRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewClassifierService {

    private final OpenAiChatModel openAiChatModel;
    private final ObjectMapper objectMapper;

    private final OpenAiApi openAiApi;

    public List<Review> classifyReviews(List<String> reviews) {

        return reviews.stream().map(this::classifyReview).toList();

    }

    private Review classifyReview(String reviewText) {
        String instruction = "Rate this review and reply in raw json format (no markdown, no whitespace) so it can be parsed by objectmappter. provide following fields: reviewCategorization, reviewCategoryExplanation, reviewResponseIdea, isAllowedForAutoPublish, locale\n"
            + "reviewCategorization may have those values: " + Arrays.stream(ReviewCategorization.values()).map(Enum::name).collect(Collectors.joining(",")) + "\n"
            + "isAllowedForAutoPublish is true of false if our application may automatically publish it when the review is positive or very positive"
            + "locale is the locale of the review, e.g. en_US, de_CH, fr_FR, it_IT";

        Prompt prompt = Prompt.builder().
            content(instruction + "\nReview: " + reviewText).
            build();

        ChatResponse response =  openAiChatModel.call(prompt);

        String rawTextToBeParsed = response.getResult().getOutput().getText();
/*
        String cleaned = rawTextToBeParsed
            .replaceAll("(?s)```json", "")
            .replaceAll("(?s)```", "")
            .trim();

        if (cleaned.startsWith("\"") && cleaned.endsWith("\"")) {
            try {
                cleaned = objectMapper.readValue(cleaned, String.class);
            } catch (JsonProcessingException e) {
                cleaned = rawTextToBeParsed;
            }
        }
*/
        try {
            Review parsedReview =  this.objectMapper.readValue(this.objectMapper.createParser(rawTextToBeParsed.getBytes()), Review.class);
            parsedReview.setReviewText(reviewText);
            return parsedReview;
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

}
