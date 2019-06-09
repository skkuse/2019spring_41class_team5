package edu.skku.dealistic.controller;

import edu.skku.dealistic.exception.NotFoundException;
import edu.skku.dealistic.model.Item;
import edu.skku.dealistic.model.ItemCategory;
import edu.skku.dealistic.persistence.ItemCategoryRepository;
import edu.skku.dealistic.persistence.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {

    private final ItemRepository itemRepository;

    private final ItemCategoryRepository itemCategoryRepository;

    @GetMapping
    public List<Item> getItems(
            @RequestParam(required = false) Integer itemCategoryId,
            @RequestParam(required = false) String keywords) {
        Optional<ItemCategory> itemCategory = itemCategoryId == null ?
                Optional.empty() : itemCategoryRepository.findById(itemCategoryId);
        //Optional<String> keywordString = keywords == null || keywords.isEmpty() ?
        //        Optional.empty() : Optional.of(keywords);
        //List<String> keywordStrings = List.of(keywords.split(","));

        if (itemCategory.isPresent())
            return itemRepository.findAllByCategory(itemCategory.get());

        else return List.of();
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable Integer id) {
        return itemRepository.findDetailById(id).orElseThrow(NotFoundException::new);
    }

    // Item is Read-only Object. No Update/Delete Method.
}
