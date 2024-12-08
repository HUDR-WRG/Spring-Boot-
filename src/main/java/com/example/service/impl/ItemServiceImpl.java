package com.example.service.impl;

import com.example.entity.Item;
import com.example.mapper.ItemMapper;
import com.example.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Override
    @Transactional
    public Item publishItem(Item item) {
        // 设置初始状态
        item.setStatus("open");
        itemMapper.insert(item);
        return item;
    }

    @Override
    public List<Item> getItemList(String category, String lostFound) {
        return itemMapper.selectList(category, lostFound);
    }

    @Override
    public Item getItemDetail(Integer itemId) {
        return itemMapper.selectById(itemId);
    }

    @Override
    @Transactional
    public Item updateItem(Item item) {
        itemMapper.update(item);
        return item;
    }

    @Override
    @Transactional
    public Boolean deleteItem(Integer itemId) {
        return itemMapper.deleteById(itemId) > 0;
    }
} 