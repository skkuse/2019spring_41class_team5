package edu.skku.dealistic.controller;

import edu.skku.dealistic.exception.NotFoundException;
import edu.skku.dealistic.model.ItemCategory;
import edu.skku.dealistic.model.Recommendation;
import edu.skku.dealistic.payload.ApiResponse;
import edu.skku.dealistic.persistence.ItemCategoryRepository;
import edu.skku.dealistic.persistence.RecommendationRepository;
import edu.skku.dealistic.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Recommendation Controller.
 * Main Features:
 * - CRUD recommendations
 *
 * @author Junhyun Kim
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/recommendations", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecommendationController {

    private final RecommendationService recommendationService;

    private final RecommendationRepository recommendationRepository;

    private final ItemCategoryRepository itemCategoryRepository;

    @GetMapping
    public List<Recommendation> getRecommendations(
            @RequestParam Integer itemCategoryId
    ) {
        ItemCategory category = itemCategoryRepository.findById(itemCategoryId).orElseThrow(NotFoundException::new);
        return recommendationRepository.findRecommendationsByCategory(category);
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
