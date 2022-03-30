package com.example.ijodiy_qahramonlar.repository;

import com.example.ijodiy_qahramonlar.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    boolean existsByNameIgnoreCase(String name);
}
