package com.example.ijodiy_qahramonlar.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotServiceImpl {

    SendMessage welcome(Update update);

    SendPhoto categories(Update update);

    SendMessage regions(Update update);

    SendMessage settings(Update update);

    SendMessage aboutUs(Update update);

    SendMessage tarjima(Update update);

    SendPhoto selectRegion(Update update);

    SendMessage selectCategory(Update update);

    SendMessage selectAuthor(Update update);

    SendMessage selectPoem(Update update);

    SendMessage showPoem(Update update);

    SendMessage showUser(Update update);

    SendMessage crudMenu(Update update);

    SendMessage sendAction(Update update);

    SendPhoto authors(Update update);
}
