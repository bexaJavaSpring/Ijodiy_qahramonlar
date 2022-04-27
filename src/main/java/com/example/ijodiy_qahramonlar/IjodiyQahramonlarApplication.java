package com.example.ijodiy_qahramonlar;

import com.example.ijodiy_qahramonlar.bot.IjodBot;
import com.example.ijodiy_qahramonlar.entity.User;
import com.example.ijodiy_qahramonlar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Scanner;

@SpringBootApplication
public class IjodiyQahramonlarApplication {
    public static void main(String[] args) {
        SpringApplication.run(IjodiyQahramonlarApplication.class, args);



    }
}
