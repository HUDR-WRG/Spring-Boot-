package com.example.service;

import com.example.entity.User;

public interface UserService {
    User register(User user);
    User login(User user);
} 