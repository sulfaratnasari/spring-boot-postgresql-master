package com.demo.postgres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.postgres.entity.Location;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
 
      @Query(value =
      "SELECT location.address, location.latitude, location.longitude, carpark.total_lots, carpark.lots_available, "+
      "sqrt(power((location.latitude - :latitude), 2) + power((location.longitude - :longitude), 2)) AS distance " +
      "FROM location " +
      "INNER JOIN carpark on location.id = carpark.carpark_location_id " +
      "WHERE carpark.lots_available != '0' " +
      "ORDER BY distance asc, carpark.lots_available asc, carpark.total_lots asc " +
      "LIMIT :limit OFFSET :offset", nativeQuery = true)
      List<Object[]> findNearestLocations(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("limit") Integer limit, @Param("offset") Integer offset);
}
