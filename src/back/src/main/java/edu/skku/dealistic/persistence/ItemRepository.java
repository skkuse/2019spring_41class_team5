package edu.skku.dealistic.persistence;

import edu.skku.dealistic.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
