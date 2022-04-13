package com.example.ijodiy_qahramonlar.repository;

import com.example.ijodiy_qahramonlar.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region,Integer> {

  //  List<Region> findAllByCategory_Name(String name);

//    boolean existsByNameIgnoreCase(String name);

    Optional<Region> findByName(String name);


}
