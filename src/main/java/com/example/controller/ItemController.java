package com.example.controller;

import com.example.common.Result;
import com.example.entity.Item;
import com.example.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {
    
    @Autowired
    private ItemService itemService;

    @PostMapping("/publish")
    public Result<Item> publishItem(@RequestBody Item item) {
        return Result.success(itemService.publishItem(item));
    }

    @GetMapping("/list")
    public Result<List<Item>> getItemList(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String lostFound) {
        return Result.success(itemService.getItemList(category, lostFound));
    }

    @GetMapping("/{itemId}")
    public Result<Item> getItemDetail(@PathVariable Integer itemId) {
        return Result.success(itemService.getItemDetail(itemId));
    }

    @PutMapping("/{itemId}")
    public Result<Item> updateItem(@PathVariable Integer itemId, @RequestBody Item item) {
        item.setItemId(itemId);
        return Result.success(itemService.updateItem(item));
    }

    @DeleteMapping("/{itemId}")
    public Result<Boolean> deleteItem(@PathVariable Integer itemId) {
        return Result.success(itemService.deleteItem(itemId));
    }
} 