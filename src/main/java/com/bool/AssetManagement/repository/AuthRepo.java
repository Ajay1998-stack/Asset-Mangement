package com.bool.AssetManagement.repository;

import com.bool.AssetManagement.domain.JwtRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthRepo extends MongoRepository<JwtRequest,Integer> {
}
