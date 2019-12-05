package com.bool.AssetManagement.repository;

import com.bool.AssetManagement.domain.Asset;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssetDetailsRepository extends MongoRepository<Asset, Integer> {
}
