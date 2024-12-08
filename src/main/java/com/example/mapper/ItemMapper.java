package com.example.mapper;

import com.example.entity.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ItemMapper {
    
    int insert(Item item);
    
    List<Item> selectList(@Param("category") String category, @Param("lostFound") String lostFound);
    
    Item selectById(@Param("itemId") Integer itemId);
    
    int update(Item item);
    
    int deleteById(@Param("itemId") Integer itemId);
} 