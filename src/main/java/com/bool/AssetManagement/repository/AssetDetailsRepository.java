package com.bool.AssetManagement.repository;

import com.bool.AssetManagement.domain.Asset;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AssetDetailsRepository extends MongoRepository<Asset, String> {
    boolean existsByRegNo(String regNo);
    long deleteByRegNo(String regNo);
    Asset findByRegNo(String regNo);
//    @Query("{ $and: [ { resolvedBy: CSR }, { 'interactions.employeeID': ?0 } ] }")


    @Query("{'regNo': ?0}, {$set:{'status': ?1}}")
    void updateAssetEntryEndride (String regNo , String status);

}
