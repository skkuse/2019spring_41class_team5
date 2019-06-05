package edu.skku.dealistic.service;

import edu.skku.dealistic.model.Item;
import edu.skku.dealistic.persistence.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    public void createItem(Item item) {
        itemRepository.save(item);
    }

    public Item getItem(Integer id) {
        return itemRepository.getOne(id);
    }

    public void updateItem(Integer id, Item item) {
        Item original = itemRepository.getOne(id);

        original.setImage(item.getImage());
        original.setOriginalRating(item.getOriginalRating());
        original.setRating(item.getRating());
        original.setSpecs(item.getSpecs());

        itemRepository.save(item);
    }

    public void deleteItem(Integer id) {
        itemRepository.deleteById(id);
    }
}
