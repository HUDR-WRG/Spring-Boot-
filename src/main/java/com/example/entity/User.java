package com.example.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Integer userId;
    private String username;
    private String password;
    private String email;
    private String phone;
    private LocalDateTime createTime;
} 