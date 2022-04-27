package com.example.ijodiy_qahramonlar.repository;

import com.example.ijodiy_qahramonlar.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Integer> {

    boolean existsByDescription(String name);
}
