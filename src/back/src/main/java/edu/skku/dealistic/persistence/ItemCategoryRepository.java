package edu.skku.dealistic.persistence;

import edu.skku.dealistic.model.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Integer> {
}
