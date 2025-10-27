package ch.roth.aipoc.controller;

import ch.roth.aipoc.model.Review;
import ch.roth.aipoc.service.ReviewClassifierService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewClassifierService reviewClassifierService;
    private final ObjectMapper objectMapper;

    @PostMapping(path = "/analyze-reviews")
    public List<Review> analyzeReviews(@RequestBody ArrayNode input) {
        List<String> reviews = this.objectMapper.convertValue(input, List.class);
        List<Review> result = this.reviewClassifierService.classifyReviews(reviews);
        return result;
    }
}
