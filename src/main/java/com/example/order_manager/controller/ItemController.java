package com.example.order_manager.controller;

import com.example.order_manager.dto.ItemRequest;
import com.example.order_manager.model.Item;
import com.example.order_manager.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item createItem(@Valid @RequestBody ItemRequest request) {
        return itemService.createItem(request);
    }

    @GetMapping
    public List<Item> itemList() {
        return itemService.itemList();
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable Long id) {
        return itemService.getItem(id);
    }

    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Long id, @Valid @RequestBody ItemRequest request) {
        return itemService.updateItem(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
    }
}
