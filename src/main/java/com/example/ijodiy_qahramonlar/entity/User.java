package com.example.ijodiy_qahramonlar.entity;

import com.example.ijodiy_qahramonlar.bot.BotState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String chatId; //
    private String fullName; //
    private String phoneNumber; //
    private String username; //
    private float lon;
    private float lat;
    private String state;
    private String lang;
}
