package com.example.mapper;

import com.example.entity.Claim;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ClaimMapper {
    
    int insert(Claim claim);
    
    List<Claim> selectList(@Param("itemId") Integer itemId, @Param("claimerId") Integer claimerId);
    
    Claim selectById(@Param("claimId") Integer claimId);
    
    int updateStatus(Claim claim);
} 