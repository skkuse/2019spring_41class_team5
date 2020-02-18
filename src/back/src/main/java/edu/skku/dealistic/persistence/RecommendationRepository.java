package edu.skku.dealistic.persistence;

import edu.skku.dealistic.model.ItemCategory;
import edu.skku.dealistic.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendationRepository extends JpaRepository<Recommendation, Integer> {

    List<Recommendation> findRecommendationsByCategory(ItemCategory category);
}
