package com.example.ijodiy_qahramonlar.repository;

import com.example.ijodiy_qahramonlar.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region,Integer> {
    Optional<Region> findByName(String name);

    boolean existsByNameIgnoreCase(String name);

}
