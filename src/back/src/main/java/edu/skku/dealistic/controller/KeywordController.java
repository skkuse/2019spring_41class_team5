package edu.skku.dealistic.controller;

import edu.skku.dealistic.model.ItemCategory;
import edu.skku.dealistic.model.Keyword;
import edu.skku.dealistic.persistence.ItemCategoryRepository;
import edu.skku.dealistic.persistence.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Keyword Controller.
 * Main Features:
 * - Provide keywords
 *
 * @author Junhyun Kim
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/keywords", produces = MediaType.APPLICATION_JSON_VALUE)
public class KeywordController {

    private final KeywordRepository keywordRepository;

    private final ItemCategoryRepository itemCategoryRepository;

    @GetMapping
    public List<Keyword> getKeywords(@RequestParam Integer itemCategoryId) {
        Optional<ItemCategory> category = itemCategoryRepository.findById(itemCategoryId);
        if (category.isPresent())
            return keywordRepository.findKeywordsByCategory(category.get());
        else
            return List.of();
    }
}
