package edu.skku.dealistic.service;

import edu.skku.dealistic.model.Recommendation;
import edu.skku.dealistic.persistence.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private RecommendationRepository recommendationRepository;

    public List<Recommendation> getRecommendations() {
        return recommendationRepository.findAll();
    }

    public Recommendation createRecommendation(Recommendation recommendation) {
        return recommendationRepository.save(recommendation);
    }

    public Recommendation getRecommendation(Integer id) {
        return recommendationRepository.getOne(id);
    }

    public void updateRecommendation(Integer id, Recommendation recommendation) {
        Recommendation retrievedRecommendation = recommendationRepository.getOne(id);

        retrievedRecommendation.setName(recommendation.getName());
        retrievedRecommendation.setCategory(recommendation.getCategory());
        retrievedRecommendation.setDescription(recommendation.getDescription());

        recommendationRepository.save(retrievedRecommendation);
    }

    public void deleteRecommendation(Integer id) {
        recommendationRepository.deleteById(id);
    }
}
