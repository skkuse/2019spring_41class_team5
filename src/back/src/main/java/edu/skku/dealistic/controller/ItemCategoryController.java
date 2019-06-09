package edu.skku.dealistic.controller;

import edu.skku.dealistic.model.ItemCategory;
import edu.skku.dealistic.service.ItemCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/item-categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemCategoryController {

    private final ItemCategoryService itemCategoryService;

    @GetMapping
    public List<ItemCategory> getItemCategories() {
        return itemCategoryService.getItemCategories();
    }
}
