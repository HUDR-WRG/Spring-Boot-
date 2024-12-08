package com.example.service;

import com.example.entity.Claim;
import java.util.List;

public interface ClaimService {
    
    Claim submitClaim(Claim claim);
    
    List<Claim> getClaimList(Integer itemId, Integer claimerId);
    
    Claim getClaimDetail(Integer claimId);
    
    Claim updateClaimStatus(Integer claimId, String status);
} 