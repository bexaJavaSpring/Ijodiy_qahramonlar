package com.example.ijodiy_qahramonlar.bot.sms;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotServiceImpl {

    SendMessage welcome(Update update);

    SendMessage selectCategory(Update update);
}
