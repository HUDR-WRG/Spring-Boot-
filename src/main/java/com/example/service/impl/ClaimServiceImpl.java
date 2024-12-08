package com.example.service.impl;

import com.example.entity.Claim;
import com.example.mapper.ClaimMapper;
import com.example.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClaimServiceImpl implements ClaimService {

    @Autowired
    private ClaimMapper claimMapper;

    @Override
    @Transactional
    public Claim submitClaim(Claim claim) {
        // 设置初始状态
        claim.setStatus("pending");
        claimMapper.insert(claim);
        return claim;
    }

    @Override
    public List<Claim> getClaimList(Integer itemId, Integer claimerId) {
        return claimMapper.selectList(itemId, claimerId);
    }

    @Override
    public Claim getClaimDetail(Integer claimId) {
        return claimMapper.selectById(claimId);
    }

    @Override
    @Transactional
    public Claim updateClaimStatus(Integer claimId, String status) {
        Claim claim = new Claim();
        claim.setClaimId(claimId);
        claim.setStatus(status);
        claimMapper.updateStatus(claim);
        return claimMapper.selectById(claimId);
    }
} 