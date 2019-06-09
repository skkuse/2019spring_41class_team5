package edu.skku.dealistic.service;

import edu.skku.dealistic.exception.NotFoundException;
import edu.skku.dealistic.model.Item;
import edu.skku.dealistic.persistence.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @Transactional
    public List<Item> getItemsOrderByRating(Integer categoryId) {
        return itemRepository.findAll()
                .stream()
                .filter(item -> item.getCategory().getId().equals(categoryId))
                .sorted(Comparator.comparingDouble(Item::getRating).reversed())
                .collect(Collectors.toList());
    }

    public void createItem(Item item) {
        itemRepository.save(item);
    }

    public Item getItem(Integer id) {
        return itemRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void updateItem(Integer id, Item item) {
        Item original = itemRepository.findById(id).orElseThrow(NotFoundException::new);

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
