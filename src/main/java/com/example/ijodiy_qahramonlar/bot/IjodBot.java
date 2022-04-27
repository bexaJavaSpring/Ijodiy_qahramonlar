package com.example.ijodiy_qahramonlar.bot;


import com.example.ijodiy_qahramonlar.entity.*;
import com.example.ijodiy_qahramonlar.repository.AuthorRepository;
import com.example.ijodiy_qahramonlar.repository.CategoryRepository;
import com.example.ijodiy_qahramonlar.repository.RegionRepository;
import com.example.ijodiy_qahramonlar.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Optional;


@Component
public class IjodBot extends TelegramLongPollingBot {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BotService botService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    RegionRepository regionRepository;
    @Autowired
    AuthorRepository authorRepository;

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

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        final String adminId = "958536406";
        User currentUser;
        SendMessage sendMessage = new SendMessage();
        if (update.hasMessage()) {
            Optional<User> optionalUser = userRepository.findByChatId(String.valueOf(update.getMessage().getChatId()));
            Message message = update.getMessage();
            if (message.hasText()) {
                if (message.getText().equals("/start")) {
                    if (optionalUser.isPresent()) {
                        currentUser = optionalUser.get();
                        currentUser.setStep(BotState.START);
                        currentUser.setState(State.DEFAULT);
                        currentUser.setFirstName(update.getMessage().getFrom().getFirstName());
                        currentUser.setUsername(update.getMessage().getFrom().getUserName());
                        currentUser.setCurrentRound(0);
                        currentUser.setLastRound(0);
                        currentUser.setAuth(false);
                        userRepository.save(currentUser);
                    } else {
                        currentUser = new User();
                        currentUser.setChatId(String.valueOf(update.getMessage().getChatId()));
                        currentUser.setStep(BotState.START);
                        currentUser.setState(State.DEFAULT);
                        currentUser.setFirstName(update.getMessage().getFrom().getFirstName());
                        currentUser.setUsername(update.getMessage().getFrom().getUserName());
                        currentUser.setCurrentRound(0);
                        currentUser.setLastRound(0);
                        currentUser.setAuth(false);
                        userRepository.save(currentUser);
                    }
                    if (currentUser.getChatId().equals(adminId)) {
                        execute(botService.adminPanel(update));
                    } else {
                        execute(botService.welcome(update));
                    }
                } else {
                    currentUser = optionalUser.get();
                    switch (currentUser.getState()) {
                        case DEFAULT -> {
                            switch (update.getMessage().getText()) {
                                case BotContains.USERS -> {
                                    execute(botService.UserList(update));
                                    currentUser.setState(State.DEFAULT);
                                    userRepository.save(currentUser);
                                    execute(botService.adminPanel(update));
                                }
                                case BotContains.CRUD_MENU -> {
                                    currentUser.setState(State.CRUD_MENU);
                                    userRepository.save(currentUser);
                                    execute(botService.crudMenu(update));
                                }
                                case BotContains.SEND_ACTION -> {
                                    currentUser.setState(State.DEFAULT);
                                    userRepository.save(currentUser);
                                }
                            }
                        }
//                        case CRUD_MENU -> {
//                            switch (update.getMessage().getText()) {
//                                case BotContains.MAIN_MENU_BUTTON -> {
//                                    currentUser.setState(State.DEFAULT);
//                                    userRepository.save(currentUser);
//                                    execute(botService.adminPanel(update));
//                                }
//                                case BotContains.BACK -> {
//                                    currentUser.setState(State.DEFAULT);
//                                    userRepository.save(currentUser);
//                                    execute(botService.adminPanel(update));
//                                }
//                                case BotContains.CATEGORY_CRUD -> {
//                                    currentUser.setState(State.CATEGORY);
//                                    userRepository.save(currentUser);
//                                    execute(botService.categoryCrud(update));
//                                }
//                                case BotContains.REGION_CRUD -> {
//                                    currentUser.setState(State.REGION);
//                                    userRepository.save(currentUser);
//                                    execute(botService.regionCrud(update));
//                                }
//                                case BotContains.AUTHOR_CRUD -> {
//                                    currentUser.setState(State.AUTHOR);
//                                    userRepository.save(currentUser);
//                                    execute(botService.authorCrud(update));
//                                }
//                                case BotContains.POEM_CRUD -> {
//                                    currentUser.setState(State.POEM);
//                                    userRepository.save(currentUser);
//                                    execute(botService.poemCrud(update));
//                                }
//                                case BotContains.POST_CRUD -> {
//                                    currentUser.setState(State.POST);
//                                    userRepository.save(currentUser);
//                                    execute(botService.postCrud(update));
//                                }
//                                case BotContains.ATTACHMENT_CRUD -> {
//                                    currentUser.setState(State.ATTACHMENT);
//                                    userRepository.save(currentUser);
//                                    execute(botService.attachmentCrud(update));
//                                }
//                            }
//                        }
//                        case CATEGORY -> {
//                            switch (update.getMessage().getText()) {
//                                case BotContains.MAIN_MENU_BUTTON -> {
//                                    currentUser.setState(State.DEFAULT);
//                                    userRepository.save(currentUser);
//                                    execute(botService.adminPanel(update));
//                                }
//                                case BotContains.BACK -> {
//                                    currentUser.setState(State.CRUD_MENU);
//                                    userRepository.save(currentUser);
//                                    execute(botService.crudMenu(update));
//                                }
//                                case BotContains.CATA -> {
//                                    sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
//                                    sendMessage.setText("Enter category name");
//                                    Category category = new Category();
//                                    if (category.getName() == null) {
//                                        category.setName(message.getText());
//                                        category.setActive(true);
//                                        sendMessage.setText("Rasm yuboring");
//                                    } else {
//                                        // rasm yuborish
//                                        sendMessage.setText("Successfully created");
//                                        categoryRepository.save(category);
//                                        category = new Category();
//                                    }
//                                    currentUser.setState(State.CATEGORY);
//                                    userRepository.save(currentUser);
//                                    botService.categoryCrud(update);
//                                    execute(sendMessage);
//                                }
//                                case BotContains.CATU -> {
//                                    sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
//                                    sendMessage.setText("Choose category");
//                                    sendMessage.setReplyMarkup(botService.updateCategory(update));
//                                    Category category = new Category();
//                                    category.setName(update.getMessage().getText());
//                                    category.setActive(true);
//                                    sendMessage.setText("Rasmini ham o'zgartirmoqchi bo'lsayiz rasm yuboring");
//                                    // rasm
//                                    categoryRepository.save(category);
//                                    sendMessage.setText("Successfully updated");
//                                    currentUser.setState(State.CATEGORY);
//                                    userRepository.save(currentUser);
//                                    botService.categoryCrud(update);
//                                    execute(sendMessage);
//                                }
//                                case BotContains.CATL -> {
//                                    sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
//                                    StringBuilder categoryList = botService.categoryList();
//                                    sendMessage.setText("<b>Categorys</b>\n" + categoryList);
//                                    sendMessage.setParseMode(ParseMode.HTML);
//                                    currentUser.setState(State.CATEGORY);
//                                    userRepository.save(currentUser);
//                                    botService.categoryCrud(update);
//                                    execute(sendMessage);
//                                }
//                                case BotContains.CATD -> {
//                                    sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
//                                    sendMessage.setText("Choose category");
//                                    sendMessage.setReplyMarkup(botService.updateCategory(update));
//                                    // write code
//                                    execute(sendMessage);
//                                }
//                            }
//                        }
//                        case REGION -> {
//                            switch (update.getMessage().getText()) {
//                                case BotContains.MAIN_MENU_BUTTON -> {
//                                    currentUser.setState(State.DEFAULT);
//                                    userRepository.save(currentUser);
//                                    execute(botService.adminPanel(update));
//                                }
//                                case BotContains.BACK -> {
//                                    currentUser.setState(State.CRUD_MENU);
//                                    userRepository.save(currentUser);
//                                    execute(botService.crudMenu(update));
//                                }
//                                case BotContains.REGA -> {
//                                    sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
//                                    sendMessage.setText("Enter region name");
//                                    Region region = new Region();
//                                    if (region.getName() == null) {
//                                        region.setName(update.getMessage().getText());
//                                    } else {
//                                        //rasm yuboriladi
//                                        sendMessage.setText("Successfully created");
//                                        regionRepository.save(region);
//                                        region = new Region();
//                                        currentUser.setState(State.REGION);
//                                        userRepository.save(currentUser);
//                                        botService.regionCrud(update);
//                                    }
//                                    execute(sendMessage);
//                                }
//                                case BotContains.REGU -> {
//
//                                    Region region = new Region();
//                                    // rasm o'zgartirish
//                                    regionRepository.save(region);
//                                    sendMessage.setText("Successfully updated");
//                                    currentUser.setState(State.REGION);
//                                    userRepository.save(currentUser);
//                                    botService.regionCrud(update);
//                                    execute(sendMessage);
//                                }
//                                case BotContains.REGL -> {
//                                    sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
//                                    StringBuilder regionList = botService.regionList();
//                                    sendMessage.setText("<b>Regions</b>\n" + regionList);
//                                    sendMessage.setParseMode(ParseMode.HTML);
//                                    currentUser.setState(State.REGION);
//                                    userRepository.save(currentUser);
//                                    botService.regionCrud(update);
//                                    execute(sendMessage);
//                                }
//                                case BotContains.REGD -> {
//                                    sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
//                                    sendMessage.setText("Choose region");
//                                    sendMessage.setReplyMarkup(botService.updateRegion(update));
//                                    // write code
//                                    execute(sendMessage);
//                                }
//                            }
//                        }
//                        case AUTHOR -> {
//                            switch (update.getMessage().getText()) {
//                                case BotContains.MAIN_MENU_BUTTON -> {
//                                    currentUser.setState(State.DEFAULT);
//                                    userRepository.save(currentUser);
//                                    execute(botService.adminPanel(update));
//                                }
//                                case BotContains.BACK -> {
//                                    currentUser.setState(State.CRUD_MENU);
//                                    userRepository.save(currentUser);
//                                    execute(botService.crudMenu(update));
//                                }
//                                case BotContains.AUTA -> {
//                                    sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
//                                    sendMessage.setText("Enter author name");
//                                    Author author = new Author();
//                                    if (author.getFullName() == null) {
//                                        author.setFullName(update.getMessage().getText());
//                                        sendMessage.setText("Enter regions ");
//                                    } else {
//                                        author.setRegions(update.getMessage().getText());
//                                        sendMessage.setText("Enter district");
//                                        author.setDistrict(update.getMessage().getText());
//                                        sendMessage.setText("Enter village");
//                                        author.setDistrict(update.getMessage().getText());
//                                        sendMessage.setText("enter birthdate ( MENTION! only enter the number)");
//                                        author.setBirthDate(update.getMessage().getText());
//                                        sendMessage.setText("Qaysi viloyatga qo'shasiz,viloyatlardan birini bosing");
//                                        botService.chooseRegion(update);
//                                        Optional<Author> byRegion_name = authorRepository.getByRegion_Name(update.getMessage().getText());
//                                        author.setRegion(byRegion_name.orElseThrow().getRegion());
//                                        // rasm yuboriladi
//
//                                        sendMessage.setText("Successfully created");
//                                        authorRepository.save(author);
//                                        author = new Author();
//                                        currentUser.setState(State.AUTHOR);
//                                        userRepository.save(currentUser);
//                                        botService.authorCrud(update);
//                                    }
//                                    execute(sendMessage);
//                                }
//                                case BotContains.AUTU -> {
//                                    sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
//                                    sendMessage.setText("Choose author:");
//                                    sendMessage.setReplyMarkup(botService.updateAuthor(update));
//                                    // write code
//                                    Author author = new Author();
//                                    author.setRegions(update.getMessage().getText());
//                                    sendMessage.setText("Enter district");
//                                    author.setDistrict(update.getMessage().getText());
//                                    sendMessage.setText("Enter village");
//                                    author.setDistrict(update.getMessage().getText());
//                                    sendMessage.setText("enter birthdate ( MENTION! only enter the number)");
//                                    author.setBirthDate(update.getMessage().getText());
//                                    sendMessage.setText("Qaysi viloyatga qo'shasiz,viloyatlardan birini bosing");
//                                    botService.chooseRegion(update);
//                                    Optional<Author> byRegion_name = authorRepository.getByRegion_Name(update.getMessage().getText());
//                                    author.setRegion(byRegion_name.orElseThrow().getRegion());
//                                    // rasm yuboriladi
//                                    authorRepository.save(author);
//                                    sendMessage.setText("Successfully updated");
//                                    currentUser.setState(State.AUTHOR);
//                                    userRepository.save(currentUser);
//                                    botService.authorCrud(update);
//                                    execute(sendMessage);
//                                }
//                                case BotContains.AUTL -> {
//                                    sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
//                                    StringBuilder authorList = botService.authorList();
//                                    sendMessage.setText("<b>Authors</b>\n" + authorList);
//                                    sendMessage.setParseMode(ParseMode.HTML);
//                                    currentUser.setState(State.AUTHOR);
//                                    userRepository.save(currentUser);
//                                    botService.authorCrud(update);
//                                    execute(sendMessage);
//                                }
//                                case BotContains.AUTD -> {
//                                    sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
//                                    sendMessage.setText("Choose author");
//                                    sendMessage.setReplyMarkup(botService.updateAuthor(update));
//                                    // write code
//                                    execute(sendMessage);
//                                }
//                            }
//                        }
//                        case POEM -> {
//                            switch (update.getMessage().getText()) {
//                                case BotContains.MAIN_MENU_BUTTON -> {
//                                    currentUser.setState(State.DEFAULT);
//                                    userRepository.save(currentUser);
//                                    execute(botService.adminPanel(update));
//                                }
//                                case BotContains.BACK -> {
//                                    currentUser.setState(State.CRUD_MENU);
//                                    userRepository.save(currentUser);
//                                    execute(botService.crudMenu(update));
//                                }
//                                case BotContains.PA -> {
//
//                                }
//                                case BotContains.PU -> {
//
//                                }
//                                case BotContains.PL -> {
//                                    sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
//                                    StringBuilder poemList = botService.poemList();
//                                    sendMessage.setText("<b>Poems</b>\n" + poemList);
//                                    sendMessage.setParseMode(ParseMode.HTML);
//                                    currentUser.setState(State.POEM);
//                                    userRepository.save(currentUser);
//                                    botService.poemCrud(update);
//                                    execute(sendMessage);
//                                }
//                                case BotContains.PD -> {
//
//                                }
//                            }
//                        }
//                        case POST -> {
//                            switch (update.getMessage().getText()) {
//                                case BotContains.MAIN_MENU_BUTTON -> {
//                                    currentUser.setState(State.DEFAULT);
//                                    userRepository.save(currentUser);
//                                    execute(botService.adminPanel(update));
//                                }
//                                case BotContains.BACK -> {
//                                    currentUser.setState(State.CRUD_MENU);
//                                    userRepository.save(currentUser);
//                                    execute(botService.crudMenu(update));
//                                }
//                                case BotContains.POA -> {
//
//                                }
//                                case BotContains.POU -> {
//
//                                }
//                                case BotContains.POL -> {
//
//                                }
//                                case BotContains.POD -> {
//
//                                }
//                            }
//                        }
//                        case ATTACHMENT -> {
//                            switch (update.getMessage().getText()) {
//                                case BotContains.MAIN_MENU_BUTTON -> {
//                                    currentUser.setState(State.DEFAULT);
//                                    userRepository.save(currentUser);
//                                    execute(botService.adminPanel(update));
//                                }
//                                case BotContains.BACK -> {
//                                    currentUser.setState(State.CRUD_MENU);
//                                    userRepository.save(currentUser);
//                                    execute(botService.crudMenu(update));
//                                }
//                                case BotContains.AA -> {
//
//                                }
//                                case BotContains.AU -> {
//
//                                }
//                                case BotContains.AL -> {
//
//                                }
//                                case BotContains.AD -> {
//
//                                }
//                            }
//                        }
                    }
                    currentUser = optionalUser.get();
                    switch (currentUser.getStep()) {
                        case BotState.START -> {
                            switch (update.getMessage().getText()) {
                                case BotContains.CATEGORIES -> {
                                    currentUser.setStep(BotState.SELECT_CAT);
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
                                case BotContains.admintoMessage -> {
                                    try {
                                        execute(botService.adminToMessage(update));
                                    } catch (TelegramApiException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        case BotState.SELECT_CAT -> {
                            switch (update.getMessage().getText()) {
                                case BotContains.MAIN_MENU_BUTTON:
                                    currentUser.setStep(BotState.START);
                                    userRepository.save(currentUser);
                                    try {
                                        execute(botService.welcome(update));
                                    } catch (TelegramApiException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case BotContains.BACK:
                                    currentUser.setStep(BotState.START);
                                    userRepository.save(currentUser);
                                    try {
                                        execute(botService.welcome(update));
                                    } catch (TelegramApiException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                default:
                                    currentUser.setStep(BotState.SELECT_REGION);
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
                                    currentUser.setStep(BotState.START);
                                    userRepository.save(currentUser);
                                    try {
                                        execute(botService.welcome(update));
                                    } catch (TelegramApiException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case BotContains.BACK:
                                    currentUser.setStep(BotState.SELECT_CAT);
                                    userRepository.save(currentUser);
                                    try {
                                        execute(botService.selectCategory(update));
                                    } catch (TelegramApiException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                default:
                                    currentUser.setStep(BotState.SELECT_AUTHOR);
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
                                    currentUser.setStep(BotState.START);
                                    userRepository.save(currentUser);
                                    try {
                                        execute(botService.welcome(update));
                                    } catch (TelegramApiException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case BotContains.BACK:
                                    try {
                                        execute(botService.regions(update));
                                    } catch (TelegramApiException e) {
                                        e.printStackTrace();
                                    }
                                    currentUser.setStep(BotState.SELECT_REGION);
                                    userRepository.save(currentUser);
                                    break;
                                default:
                                    currentUser.setStep(BotState.SELECT_POEM);
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
                                    currentUser.setStep(BotState.START);
                                    userRepository.save(currentUser);
                                    try {
                                        execute(botService.welcome(update));
                                    } catch (TelegramApiException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case BotContains.BACK:
                                    try {
                                        execute(botService.selectAuthor(update));
                                    } catch (TelegramApiException e) {
                                        e.printStackTrace();
                                    }
                                    currentUser.setStep(BotState.SELECT_AUTHOR);
                                    userRepository.save(currentUser);
                                    break;
                                default:
                                    try {
                                        execute(botService.showPoem(update));
                                    } catch (TelegramApiException e) {
                                        e.printStackTrace();
                                    }
//                                    userRepository.save(currentUser);
//                                    currentUser.setStep(BotState.SELECT_POEM);
                                    execute(botService.selectPoem(update));
                            }
                        }
                    }
                }
            }
        }
    }

}






