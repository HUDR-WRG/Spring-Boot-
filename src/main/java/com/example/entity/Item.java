package com.example.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Item {
    private Integer itemId;
    private Integer userId;
    private String itemName;
    private String description;
    private String category;
    private String lostFound;
    private String location;
    private String imageUrl;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 