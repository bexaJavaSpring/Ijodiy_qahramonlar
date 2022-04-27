package com.example.ijodiy_qahramonlar.bot;

import com.example.ijodiy_qahramonlar.entity.Post;
import com.example.ijodiy_qahramonlar.entity.User;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface BotServiceImpl {

    SendMessage welcome(Update update);

    SendPhoto categories(Update update);

    SendMessage regions(Update update);

    SendMessage aboutUs(Update update);

    SendMessage tarjima(Update update);

    SendPhoto selectRegion(Update update);

    SendMessage selectCategory(Update update);

    SendMessage selectAuthor(Update update);

    SendMessage selectPoem(Update update);

    SendMessage showPoem(Update update);

    SendDocument UserList(Update update);

    SendMessage crudMenu(Update update);

    SendPhoto sendNotification(Long chatId,Post post);

    SendPhoto authors(Update update);

    SendMessage adminToMessage(Update update);


}
