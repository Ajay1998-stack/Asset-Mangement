package com.bool.AssetManagement.repository;

import com.bool.AssetManagement.domain.Asset;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssetDetailsRepository extends MongoRepository<Asset, String> {
    boolean existsByRegNo(String regNo);
    long deleteByRegNo(String regNo);
}
