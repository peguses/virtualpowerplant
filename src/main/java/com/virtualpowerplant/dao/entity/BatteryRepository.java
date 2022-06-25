package com.virtualpowerplant.dao.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatteryRepository extends JpaRepository<Battery, Integer> {
    @Query(
            value = "Select * from (SELECT * FROM BATTERIES " +
                    "where postcode BETWEEN :minPostcode and :maxPostcode order by postcode asc) order by name asc",
            nativeQuery = true)
    List<Battery> findPostcodeBetweenNative(@Param("minPostcode") Integer minPostcode,
                                            @Param("maxPostcode") Integer maxPostcode);
}
