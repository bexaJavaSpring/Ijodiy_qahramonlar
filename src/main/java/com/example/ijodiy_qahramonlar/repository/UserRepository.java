package com.example.ijodiy_qahramonlar.repository;

import com.example.ijodiy_qahramonlar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByChatId(String chatId);
}
