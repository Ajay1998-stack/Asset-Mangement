package com.bool.AssetManagement.repository;

import com.bool.AssetManagement.domain.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface VehicleRepository extends MongoRepository<Vehicle, Integer> {
//    @Query("db.Vehicle.find({$and:[{"no":"no"},{"ridecount":"ridecount"}]}).pretty()")
//            "SELECT v FROM Vehicle v WHERE WHEREv.no = :no AND v.rideCount= :rideCount"
    @Query(value = "{'id': ?0 , 'rideCount': ?1}")
    Vehicle rideOfVehicle(int id,int rideCount);
}
