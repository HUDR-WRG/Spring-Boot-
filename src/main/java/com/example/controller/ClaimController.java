package com.example.controller;

import com.example.common.Result;
import com.example.entity.Claim;
import com.example.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/claim")
public class ClaimController {
    
    @Autowired
    private ClaimService claimService;

    @PostMapping("/submit")
    public Result<Claim> submitClaim(@RequestBody Claim claim) {
        return Result.success(claimService.submitClaim(claim));
    }

    @GetMapping("/list")
    public Result<List<Claim>> getClaimList(
            @RequestParam(required = false) Integer itemId,
            @RequestParam(required = false) Integer claimerId) {
        return Result.success(claimService.getClaimList(itemId, claimerId));
    }

    @GetMapping("/{claimId}")
    public Result<Claim> getClaimDetail(@PathVariable Integer claimId) {
        return Result.success(claimService.getClaimDetail(claimId));
    }

    @PutMapping("/{claimId}/status")
    public Result<Claim> updateClaimStatus(
            @PathVariable Integer claimId,
            @RequestParam String status) {
        return Result.success(claimService.updateClaimStatus(claimId, status));
    }
} 