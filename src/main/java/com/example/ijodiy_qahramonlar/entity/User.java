package com.example.ijodiy_qahramonlar.entity;

import com.example.ijodiy_qahramonlar.bot.BotState;
import com.example.ijodiy_qahramonlar.bot.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String chatId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String username;
    private boolean isAuth;
    private String step;
    private State state;
    private int lastRound;
    private int currentRound;

    public User(Integer id, int lastRound, int currentRound, boolean isAuth) {
        this.id = id;
        this.isAuth = isAuth;
        this.lastRound = lastRound;
        this.currentRound = currentRound;
    }


}
