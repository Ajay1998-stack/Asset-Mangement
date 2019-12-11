package com.bool.AssetManagement.repository;

import com.bool.AssetManagement.domain.DataAccessObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthDetailsRepository extends MongoRepository<DataAccessObject,Integer> {
    DataAccessObject findByRegNo(String regNo);
    boolean existsByRegNo(String regNo);
}
