package com.example.ijodiy_qahramonlar.repository;

import com.example.ijodiy_qahramonlar.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author,Integer> {

    boolean existsByFullNameIgnoreCase(String fullname);

    Optional<Author> findByFullName(String fullName);

    List<Author> findByRegion_Name(String name);
}
