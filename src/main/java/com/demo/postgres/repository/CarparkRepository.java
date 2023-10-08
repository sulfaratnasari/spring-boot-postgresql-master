package com.demo.postgres.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.demo.postgres.entity.Carpark;

@Repository
public interface CarparkRepository extends JpaRepository<Carpark, Long>{
    List<Carpark> findByCarparkLocationID(Long carparkLocationID);
  /*  @Query(value =
      "SELECT * from carpark where carpark_location_id = :carparkLocationID", nativeQuery = true)
      List<Object[]> findCarparkByLocationID(@Param("carparkLocationID") Long carparkLocationID); */
}