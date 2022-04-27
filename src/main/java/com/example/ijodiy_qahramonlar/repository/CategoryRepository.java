package com.example.ijodiy_qahramonlar.repository;

import com.example.ijodiy_qahramonlar.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    Optional<Category> findByNameContaining(String name);

    boolean existsByNameIgnoreCase(String name);

}
