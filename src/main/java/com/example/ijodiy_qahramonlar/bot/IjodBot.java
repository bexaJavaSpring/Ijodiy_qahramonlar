package com.example.ijodiy_qahramonlar.bot;

import com.example.ijodiy_qahramonlar.entity.User;
import com.example.ijodiy_qahramonlar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IjodBot extends TelegramLongPollingBot {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BotService botService;
    @Value("${telegram_bot_botToken}")
    private String token;
    @Value("${telegram_bot_username}")
    private String username;

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        final String adminChatId1 = "958536406";
        final String adminChatId2="1411561011";
        User currentUser;
        User admin;
        if (update.hasMessage()) {
            Optional<User> optionalUser = userRepository.findByChatId(String.valueOf(update.getMessage().getChatId()));
            Message message = update.getMessage();
            if (message.hasText()) {
                if (message.getText().equalsIgnoreCase("/start")) {
                    if (optionalUser.isPresent()) {
                        currentUser = optionalUser.get();
                        currentUser.setState(BotState.START);
                        currentUser.setFullName(update.getMessage().getFrom().getFirstName());
                        currentUser.setUsername(update.getMessage().getFrom().getUserName());
                        userRepository.save(currentUser);
                    } else {

                        currentUser = new User();
                        currentUser.setChatId(String.valueOf(update.getMessage().getChatId()));
                        currentUser.setState(BotState.START);
                        userRepository.save(currentUser);
                    }
                    if(message.getChatId().equals(adminChatId1) || message.getChatId().equals(adminChatId2)) {
                        try {
                            execute(botService.adminPanel(update));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }else {

                        // admin panel switch case yziladi
                        try {
                            execute(botService.welcome(update));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    currentUser = optionalUser.get();
                    if(currentUser.getChatId().equals(adminChatId1)){
                       switch (currentUser.getState()){
                           case BotState.START -> {
                              switch (update.getMessage().getText()){
                                  case BotContains.USERS -> {
                                      currentUser.setState(BotState.SHOW_USER);
                                      userRepository.save(currentUser);
                                      try {
                                          execute(botService.showUser(update));
                                      } catch (TelegramApiException e) {
                                          e.printStackTrace();
                                      }
                                  }
                                  case BotContains.SEND_ACTION -> {
                                      try {
                                          execute(botService.sendAction(update));

                                      } catch (TelegramApiException e) {
                                          e.printStackTrace();
                                      }
                                  }
                                  case BotContains.CRUD_MENU -> {
                                      try {
                                          execute(botService.crudMenu(update));
                                      }catch (TelegramApiException e){
                                          e.printStackTrace();
                                      }
                                  }
                                  case BotContains.admintoMessage -> {
                                      try {
                                          execute(botService.adminToMessage(update));
                                      }catch (TelegramApiException e){
                                          e.printStackTrace();
                                      }
                                  }
                              }
                           }
                       }
                    }else {
                        switch (currentUser.getState()) {
                            case BotState.START -> {
                                switch (update.getMessage().getText()) {
                                    case BotContains.CATEGORIES -> {
                                        currentUser.setState(BotState.SELECT_CAT);
                                        userRepository.save(currentUser);
                                        try {
                                            execute(botService.selectCategory(update));
                                        } catch (TelegramApiException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    case BotContains.Loyiha_mualliflari -> {
                                        try {
                                            execute(botService.aboutUs(update));
                                        } catch (TelegramApiException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    case BotContains.TARJIMA -> {
                                        try {
                                            execute(botService.tarjima(update));
                                        } catch (TelegramApiException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                            case BotState.SELECT_CAT -> {
                                switch (update.getMessage().getText()) {
                                    case BotContains.MAIN_MENU_BUTTON:
                                        currentUser.setState(BotState.START);
                                        userRepository.save(currentUser);
                                        try {
                                            execute(botService.welcome(update));
                                        } catch (TelegramApiException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    case BotContains.BACK:
                                        currentUser.setState(BotState.START);
                                        userRepository.save(currentUser);
                                        try {
                                            execute(botService.welcome(update));
                                        } catch (TelegramApiException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    default:
                                        currentUser.setState(BotState.SELECT_REGION);
                                        userRepository.save(currentUser);
                                        try {
                                            execute(botService.categories(update));
                                            execute(botService.regions(update));
                                        } catch (TelegramApiException e) {
                                            e.printStackTrace();
                                        }
                                }
                            }
                            case BotState.SELECT_REGION -> {
                                switch (update.getMessage().getText()) {
                                    case BotContains.MAIN_MENU_BUTTON:
                                        currentUser.setState(BotState.START);
                                        userRepository.save(currentUser);
                                        try {
                                            execute(botService.welcome(update));
                                        } catch (TelegramApiException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    case BotContains.BACK:
                                        currentUser.setState(BotState.SELECT_CAT);
                                        userRepository.save(currentUser);
                                        try {
                                            execute(botService.selectCategory(update));
                                        } catch (TelegramApiException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    default:
                                        currentUser.setState(BotState.SELECT_AUTHOR);
                                        userRepository.save(currentUser);
                                        try {
                                            execute(botService.selectRegion(update));
                                            execute(botService.selectAuthor(update));
                                        } catch (TelegramApiException e) {
                                            e.printStackTrace();
                                        }
                                }
                            }
                            case BotState.SELECT_AUTHOR -> {
                                switch (update.getMessage().getText()) {
                                    case BotContains.MAIN_MENU_BUTTON:
                                        currentUser.setState(BotState.START);
                                        userRepository.save(currentUser);
                                        try {
                                            execute(botService.welcome(update));
                                        } catch (TelegramApiException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    case BotContains.BACK:
                                        currentUser.setState(BotState.SELECT_REGION);
                                        userRepository.save(currentUser);
                                        try {
                                            execute(botService.regions(update));
                                        } catch (TelegramApiException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    default:
                                        currentUser.setState(BotState.SELECT_POEM);
                                        userRepository.save(currentUser);
                                        try {
                                            execute(botService.authors(update));
                                            execute(botService.selectPoem(update));
                                        } catch (TelegramApiException e) {
                                            e.printStackTrace();
                                        }
                                }
                            }
                            case BotState.SELECT_POEM -> {
                                switch (update.getMessage().getText()) {
                                    case BotContains.MAIN_MENU_BUTTON:
                                        currentUser.setState(BotState.START);
                                        userRepository.save(currentUser);
                                        try {
                                            execute(botService.welcome(update));
                                        } catch (TelegramApiException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    case BotContains.BACK:
                                        currentUser.setState(BotState.SELECT_AUTHOR);
                                        userRepository.save(currentUser);
                                        try {
                                            execute(botService.selectAuthor(update));
                                        } catch (TelegramApiException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    default:
                                        currentUser.setState(BotState.FINISH);
                                        userRepository.save(currentUser);
                                        try {
                                            execute(botService.showPoem(update));
                                        } catch (TelegramApiException e) {
                                            e.printStackTrace();
                                        }
                                }
                            }
                            case BotState.FINISH -> {
                                switch (update.getMessage().getText()) {
                                    case BotContains.MAIN_MENU_BUTTON:
                                        currentUser.setState(BotState.START);
                                        userRepository.save(currentUser);
                                        try {
                                            execute(botService.welcome(update));
                                        } catch (TelegramApiException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    case BotContains.BACK:
                                        currentUser.setState(BotState.SELECT_POEM);
                                        userRepository.save(currentUser);
                                        try {
                                            execute(botService.selectPoem(update));
                                        } catch (TelegramApiException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}




