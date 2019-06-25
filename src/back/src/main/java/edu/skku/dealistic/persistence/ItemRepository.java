package edu.skku.dealistic.persistence;

import edu.skku.dealistic.model.Item;
import edu.skku.dealistic.model.ItemCategory;
import edu.skku.dealistic.model.ItemKeyword;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findItemsByCategory(ItemCategory category);

    @EntityGraph("Item.detail")
    Optional<Item> findDetailById(Integer id);

    List<Item> findByNameContaining(String name);
}
