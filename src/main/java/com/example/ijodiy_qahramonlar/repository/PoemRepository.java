package com.example.ijodiy_qahramonlar.repository;

import com.example.ijodiy_qahramonlar.entity.Author;
import com.example.ijodiy_qahramonlar.entity.Poem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PoemRepository extends JpaRepository<Poem,Integer> {

    boolean existsByNameIgnoreCase(String name);

    Optional<Poem> findByName(String name);

    List<Poem> findByAuthorFullName(String name);
}
