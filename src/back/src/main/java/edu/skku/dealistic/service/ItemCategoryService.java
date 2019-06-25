package edu.skku.dealistic.service;

import edu.skku.dealistic.model.ItemCategory;
import edu.skku.dealistic.persistence.ItemCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Item Category Service.
 * Main Features:
 * - Provide Item Category
 *
 * @author Junhyun Kim
 */
@Service
@RequiredArgsConstructor
public class ItemCategoryService {

    private final ItemCategoryRepository itemCategoryRepository;


    public List<ItemCategory> getItemCategories() {
        return itemCategoryRepository.findAll();
    }

    public ItemCategory getItemCategory(Integer id) {
        return itemCategoryRepository.getOne(id);
    }
}
