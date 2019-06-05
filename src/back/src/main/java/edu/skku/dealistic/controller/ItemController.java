package edu.skku.dealistic.controller;

import edu.skku.dealistic.model.ApiResponse;
import edu.skku.dealistic.model.Item;
import edu.skku.dealistic.service.ItemService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(name = "items", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> getItems() {
        return itemService.getItems();
    }

    @GetMapping("{id}")
    public Item getItem(@PathVariable Integer id) {
        return itemService.getItem(id);
    }

    // Item is Read-only Object
}
