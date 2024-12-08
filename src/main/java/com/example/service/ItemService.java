package com.example.service;

import com.example.entity.Item;
import java.util.List;

public interface ItemService {
    
    Item publishItem(Item item);
    
    List<Item> getItemList(String category, String lostFound);
    
    Item getItemDetail(Integer itemId);
    
    Item updateItem(Item item);
    
    Boolean deleteItem(Integer itemId);
} 