package com.example.controller;

import com.example.common.Result;
import com.example.dto.LoginDTO;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {
    
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired(required = false)
    private UserMapper userMapper;

    @PostMapping("/login")
    public Result<User> login(@RequestBody LoginDTO loginDTO) {
        logger.debug("接收到登录请求");
        logger.info("开始登录，用户名：{}", loginDTO.getUsername());
        
        // 检查userMapper是否注入成功
        if (userMapper == null) {
            logger.error("UserMapper未注入成功");
            return Result.error("系统错误：UserMapper未注入");
        }

        // 参数校验
        if (loginDTO.getUsername() == null || loginDTO.getPassword() == null) {
            logger.warn("用户名或密码为空");
            return Result.error("用户名和密码不能为空");
        }

        try {
            logger.debug("开始查询用户信息");
            // 查询用户
            User user = userMapper.findByUsername(loginDTO.getUsername());
            logger.info("查询用户结果：{}", user);
            
            if (user == null) {
                logger.warn("用户不存在：{}", loginDTO.getUsername());
                return Result.error("用户不存在");
            }

            // 密码验证
            if (!loginDTO.getPassword().equals(user.getPassword())) {
                logger.warn("密码错误，用户名：{}", loginDTO.getUsername());
                return Result.error("密码错误");
            }

            // 清空密码后返回
            user.setPassword(null);
            logger.info("登录成功，用户名：{}", loginDTO.getUsername());
            return Result.success(user);
            
        } catch (Exception e) {
            logger.error("登录异常", e);
            // 打印完整的异常堆栈
            e.printStackTrace();
            return Result.error("登录失败：" + e.getMessage() + "，异常类型：" + e.getClass().getName());
        }
    }

    // 添加测试接口
    @GetMapping("/test")
    public Result<String> test() {
        return Result.success("API正常");
    }
}