package edu.skku.dealistic.controller;

import edu.skku.dealistic.model.Recommendation;
import edu.skku.dealistic.payload.ApiResponse;
import edu.skku.dealistic.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/recommendations", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping
    public List<Recommendation> getRecommendations() {
        return recommendationService.getRecommendations();
    }

    @GetMapping("/{id}")
    public Recommendation getRecommendation(@PathVariable Integer id) {
        return recommendationService.getRecommendation(id);
    }

    @PostMapping
    public Recommendation createRecommendation(@RequestBody Recommendation recommendation) {
        return recommendationService.createRecommendation(recommendation);
    }

    @PatchMapping("/{id}")
    public ApiResponse updateRecommendation(@PathVariable Integer id, @RequestBody Recommendation recommendation) {
        recommendationService.updateRecommendation(id, recommendation);
        return ApiResponse.SUCCESS;
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteRecommendation(@PathVariable Integer id) {
        recommendationService.deleteRecommendation(id);
        return ApiResponse.SUCCESS;
    }
}
