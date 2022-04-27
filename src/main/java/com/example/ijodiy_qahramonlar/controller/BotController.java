package com.example.ijodiy_qahramonlar.controller;

import com.example.ijodiy_qahramonlar.bot.BotService;
import com.example.ijodiy_qahramonlar.bot.IjodBot;
import com.example.ijodiy_qahramonlar.entity.Post;
import com.example.ijodiy_qahramonlar.entity.User;
import com.example.ijodiy_qahramonlar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@RequestMapping("/api/bot")
@RestController
@RequiredArgsConstructor
public class BotController {

    final IjodBot ijodBot;
    final UserRepository userRepository;
    final BotService botService;
    @PostMapping("/add")
    public void sendMessage(@RequestBody Post post){
        List<User> userList = userRepository.findAllByChatIdNotNull();
//        List<String> listChatId = new ArrayList<>();

        for (User user : userList) {
            String chatId = user.getChatId();
            try {
                ijodBot.execute(botService.sendNotification(Long.valueOf(chatId),post));
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
