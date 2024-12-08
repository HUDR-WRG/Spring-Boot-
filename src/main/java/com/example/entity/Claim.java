package com.example.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Claim {
    private Integer claimId;
    private Integer itemId;
    private Integer claimerId;
    private LocalDateTime claimTime;
    private String status;
    private String message;
} 