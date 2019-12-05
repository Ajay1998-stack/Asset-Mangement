package com.bool.AssetManagement.repository;

import com.bool.AssetManagement.domain.AssetHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface VehicleRepository extends MongoRepository<AssetHistory, Integer> {
//            "SELECT v FROM Vehicle v WHERE WHEREv.no = :no AND v.rideCount= :rideCount"
    @Query(value = "{'id': ?0 , 'rideCount': ?1}")
    AssetHistory rideOfVehicle(int id, int rideCount);
}
