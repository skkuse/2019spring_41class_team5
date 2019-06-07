package edu.skku.dealistic.controller;

import edu.skku.dealistic.model.Item;
import edu.skku.dealistic.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<Item> getItems() {
        return itemService.getItems();
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable Integer id) {
        return itemService.getItem(id);
    }

    // Item is Read-only Object
}
