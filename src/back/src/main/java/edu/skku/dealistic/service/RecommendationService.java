package edu.skku.dealistic.service;

import edu.skku.dealistic.model.Recommendation;
import edu.skku.dealistic.model.User;
import edu.skku.dealistic.persistence.RecommendationRepository;
import edu.skku.dealistic.persistence.UserRepository;
import edu.skku.dealistic.security.UserAuthService;
import edu.skku.dealistic.task.ReviewCrawlingTask;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Recommendation Service.
 * Main Features:
 * - CRUD Recommendation Object.
 *
 * @author Junhyun Kim,
 */
@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;

    private final UserAuthService userAuthService;

    public List<Recommendation> getRecommendations() {
        return recommendationRepository.findAll();
    }

    public Recommendation createRecommendation(Recommendation recommendation) {
        User loginUser = userAuthService.getLoginUser();
        recommendation.setAuthor(loginUser);
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
