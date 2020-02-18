package edu.skku.dealistic.controller;

import edu.skku.dealistic.exception.NotFoundException;
import edu.skku.dealistic.model.Item;
import edu.skku.dealistic.model.ItemCategory;
import edu.skku.dealistic.model.ItemKeyword;
import edu.skku.dealistic.model.Recommendation;
import edu.skku.dealistic.persistence.ItemCategoryRepository;
import edu.skku.dealistic.persistence.ItemRepository;
import edu.skku.dealistic.persistence.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Item Controller.
 * Main Features:
 * - Provide items (not user-modifiable)
 *
 * @author Junhyun Kim
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {

    private final ItemRepository itemRepository;

    private final ItemCategoryRepository itemCategoryRepository;

    private final RecommendationRepository recommendationRepository;

    @GetMapping
    public List<Item> getItems(
            @RequestParam(required = false) Integer itemCategoryId,
            @RequestParam(required = false) Integer recommendationId,
            @RequestParam(required = false) String itemName) {
        Optional<ItemCategory> itemCategory = itemCategoryId == null ?
                Optional.empty() : itemCategoryRepository.findById(itemCategoryId);
        Optional<Recommendation> recommendation = recommendationId == null ?
                Optional.empty() : recommendationRepository.findById(recommendationId);

        if (itemCategory.isPresent())
            return itemRepository.findItemsByCategory(itemCategory.get());
        if (recommendation.isPresent())
            return itemRepository
                    .findItemsByCategory(recommendation.get().getCategory())
                    .stream()
                    .filter(item ->
                            recommendation.get()
                                    .getKeywords()
                                    .stream()
                                    .allMatch(keyword ->
                                            item
                                                    .getKeywords()
                                                    .stream()
                                                    .filter(kw -> kw.getKeyword().equals(keyword))
                                                    .anyMatch(ItemKeyword::isPositive)
                                    )
                    ).collect(Collectors.toList());
        if (itemName != null && !itemName.isEmpty())
            return itemRepository.findByNameContaining(itemName);

        else return List.of();
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable Integer id) {
        return itemRepository.findDetailById(id).orElseThrow(NotFoundException::new);
    }

    // Item is Read-only Object. No Update/Delete Method.
}
