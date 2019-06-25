package edu.skku.dealistic.persistence;

import edu.skku.dealistic.model.ItemCategory;
import edu.skku.dealistic.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Integer> {

    List<Keyword> findKeywordsByCategory(ItemCategory category);
}
