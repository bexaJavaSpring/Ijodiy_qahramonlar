package com.example.ijodiy_qahramonlar.repository;

import com.example.ijodiy_qahramonlar.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Integer> {

    boolean existsByFullNameIgnoreCase(String fullname);
}
