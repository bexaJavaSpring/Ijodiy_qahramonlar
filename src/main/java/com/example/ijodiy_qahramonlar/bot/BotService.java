package com.example.ijodiy_qahramonlar.bot;

import com.example.ijodiy_qahramonlar.entity.*;
import com.example.ijodiy_qahramonlar.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BotService implements BotServiceImpl {
    static String chatId = null;
    final UserRepository userRepository;
    final RegionRepository regionRepository;
    final AttachmentRepository attachmentRepository;
    final AttachmentContentRepository attachmentContentRepository;
    final CategoryRepository categoryRepository;
    final AuthorRepository authorRepository;
    final PoemRepository poemRepository;

    @Override
    public SendMessage welcome(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText("Salom " + update.getMessage().getFrom().getFirstName() + " " + (update.getMessage().getFrom().getLastName() == null ? "" : update.getMessage().getFrom().getLastName()));
        sendMessage.setText("Ijodkorlar ijodi botiga xush kelipsiz!");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardButton button1=new KeyboardButton(BotContains.CATEGORIES);
        KeyboardButton button2 = new KeyboardButton(BotContains.SETTINGS);
        KeyboardButton button3 = new KeyboardButton(BotContains.ABOUT_US);
        KeyboardButton button4 = new KeyboardButton(BotContains.Tarjima);
        row1.add(button1);
        row1.add(button2);
        row2.add(button3);
        row2.add(button4);
        keyboardRowList.add(row1);
        keyboardRowList.add(row2);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    @Override
    public SendMessage selectCategory(Update update) {
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText(BotContains.SELECT_CATEGORY);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        List<Category> all = categoryRepository.findAll();
        int size = all.size();
        int r = (size % 2 == 1) ? size - 1 : size;
        for (int i = 0; i < r; i = i + 2) {
            KeyboardRow row1 = new KeyboardRow();
            KeyboardButton button = new KeyboardButton(all.get(i).getName());
            KeyboardButton button1 = new KeyboardButton(all.get(i + 1).getName());
            row1.add(button);
            row1.add(button1);
            keyboardRowList.add(row1);
        }
        if (size % 2 == 1) {
            KeyboardRow row = new KeyboardRow();
            KeyboardButton keyboardButton = new KeyboardButton();
            keyboardButton.setText(all.get(size - 1).getName());
            row.add(keyboardButton);
            keyboardRowList.add(row);
        }
        KeyboardRow row = new KeyboardRow();
        KeyboardButton back = new KeyboardButton(BotContains.BACK);
        KeyboardButton mainMenu = new KeyboardButton(BotContains.MAIN_MENU_BUTTON);
        row.add(back);
        row.add(mainMenu);
        keyboardRowList.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    @Override
    public SendMessage regions(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText(BotContains.SELECT_REGION);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        List<Region> all = regionRepository.findAll();
        int size = all.size();
        int r = (size % 2 == 1) ? size - 1 : size;
        for (int i = 0; i < r; i = i + 2) {
            KeyboardRow row1 = new KeyboardRow();
            KeyboardButton button = new KeyboardButton(all.get(i).getName());
            KeyboardButton button1 = new KeyboardButton(all.get(i + 1).getName());
            row1.add(button);
            row1.add(button1);
            keyboardRowList.add(row1);
        }
        if (size % 2 == 1) {
            KeyboardRow row = new KeyboardRow();
            KeyboardButton keyboardButton = new KeyboardButton();
            keyboardButton.setText(all.get(size - 1).getName());
            row.add(keyboardButton);
            keyboardRowList.add(row);
        }
        KeyboardRow row = new KeyboardRow();
        KeyboardButton back = new KeyboardButton(BotContains.BACK);
        KeyboardButton mainMenu = new KeyboardButton(BotContains.MAIN_MENU_BUTTON);
        row.add(back);
        row.add(mainMenu);
        keyboardRowList.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    @Override
    public SendMessage settings(Update update) {
        return null;
    }

    @Override
    public SendMessage aboutUs(Update update) {
        return null;
    }


    @Override
    public SendPhoto selectRegion(Update update) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(String.valueOf(update.getMessage().getChatId()));

        Optional<Region> optionalRegion = regionRepository.findByName(update.getMessage().getText());
        Region region=null;
        if (optionalRegion.isPresent()) {
            region = optionalRegion.get();
        }
        Attachment attachment = region.getAttachment();

        Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(attachment.getId());
       AttachmentContent attachmentContent=null;
        if (optionalAttachmentContent.isPresent()) {
             attachmentContent = optionalAttachmentContent.get();
        }
        InputFile inputFile = new InputFile(new ByteArrayInputStream(attachmentContent.getAsosiyContent()), attachmentContent.getAttachment().getFileOriginalName());
        sendPhoto.setPhoto(inputFile);
        return sendPhoto;
    }

    @Override
    public SendPhoto categories(Update update) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(String.valueOf(update.getMessage().getChatId()));
        Optional<Category> optionalCategory = categoryRepository.findByNameContaining(update.getMessage().getText());
        Category category=null;
        if (optionalCategory.isPresent()) {
                 category = optionalCategory.get();
        }
            Attachment attachment = category.getAttachment();

        Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(attachment.getId());
       AttachmentContent attachmentContent=null;
        if (optionalAttachmentContent.isPresent()) {
             attachmentContent = optionalAttachmentContent.get();
        }
            InputFile inputFile = new InputFile(new ByteArrayInputStream(attachmentContent.getAsosiyContent()), attachmentContent.getAttachment().getFileOriginalName());
            sendPhoto.setPhoto(inputFile);
            regions(update);
            return sendPhoto;
    }

    @Override
    public SendMessage selectAuthor(Update update) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText(BotContains.SELECT_AUTHOR);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        String regionName=update.getMessage().getText();
        List<Author> all = authorRepository.findByRegion_Name(regionName);
        int size = all.size();
        int r = (size % 2 == 1) ? size - 1 : size;
        for (int i = 0; i < r; i = i + 2) {
            KeyboardRow row1 = new KeyboardRow();
            KeyboardButton button = new KeyboardButton(all.get(i).getFullName());
            KeyboardButton button1 = new KeyboardButton(all.get(i + 1).getFullName());
            row1.add(button);
            row1.add(button1);
            keyboardRowList.add(row1);
        }
        if (size % 2 == 1) {
            KeyboardRow row = new KeyboardRow();
            KeyboardButton keyboardButton = new KeyboardButton();
            keyboardButton.setText(all.get(size - 1).getFullName());
            row.add(keyboardButton);
            keyboardRowList.add(row);
        }

        KeyboardRow row = new KeyboardRow();
        KeyboardButton back = new KeyboardButton(BotContains.BACK);
        KeyboardButton mainMenu = new KeyboardButton(BotContains.MAIN_MENU_BUTTON);
        row.add(back);
        row.add(mainMenu);
        keyboardRowList.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    @Override
    public SendMessage selectPoem(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText(BotContains.SELECT_POEM);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        String authorName=update.getMessage().getText();
        List<Poem> all = poemRepository.findByAuthorFullName(authorName);
        int size = all.size();
        int r = (size % 2 == 1) ? size - 1 : size;
        for (int i = 0; i < r; i = i + 2) {
            KeyboardRow row1 = new KeyboardRow();
            KeyboardButton button = new KeyboardButton(all.get(i).getName());
            KeyboardButton button1 = new KeyboardButton(all.get(i + 1).getName());
            row1.add(button);
            row1.add(button1);
            keyboardRowList.add(row1);
        }
        if (size % 2 == 1) {
            KeyboardRow row = new KeyboardRow();
            KeyboardButton keyboardButton = new KeyboardButton();
            keyboardButton.setText(all.get(size - 1).getName());
            row.add(keyboardButton);
            keyboardRowList.add(row);
        }

        KeyboardRow row = new KeyboardRow();
        KeyboardButton back = new KeyboardButton(BotContains.BACK);
        KeyboardButton mainMenu = new KeyboardButton(BotContains.MAIN_MENU_BUTTON);
        row.add(back);
        row.add(mainMenu);
        keyboardRowList.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }
    @Override
    public SendMessage showPoem(Update update) {
       SendMessage sendMessage=new SendMessage();
       sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        String poemName=update.getMessage().getText();
        Optional<Poem> all = poemRepository.findByName(poemName);
        Poem poem1 = all.get();
        sendMessage.setText("***************"+"\n\n"+poem1.getDescription()+"\n\n"+"******************"+"\n"+"t.me/creative_you_bot");
        KeyboardRow row = new KeyboardRow();
        KeyboardButton back = new KeyboardButton(BotContains.BACK);
        KeyboardButton mainMenu = new KeyboardButton(BotContains.MAIN_MENU_BUTTON);
        row.add(back);
        row.add(mainMenu);
        keyboardRowList.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

       return sendMessage;
    }

    @Override
    public SendMessage showUser(Update update) {
       return null;
    }

    @Override
    public SendMessage crudMenu(Update update) {
        return null;
    }

    @Override
    public SendMessage sendAction(Update update) {
        return null;
    }

    @Override
    public SendPhoto authors(Update update) {
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(String.valueOf(update.getMessage().getChatId()));

        Optional<Author> optionalAuthor = authorRepository.findByFullName(update.getMessage().getText());

        Author author = optionalAuthor.get();
        Attachment attachment = author.getAttachment();
        Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(attachment.getId());
        AttachmentContent attachmentContent = optionalAttachmentContent.get();
        StringBuilder builder = new StringBuilder();
        builder.append(author.getFullName())
                .append(author.getAge())
                .append("\n")
                .append(author.getBirthDate())
                .append("\n")
                .append(author.getStreet())
                .append("\n")
                .append(author.getCity())
                .append("\n");
        sendPhoto.setCaption(String.valueOf(builder));
        InputFile inputFile = new InputFile(new ByteArrayInputStream(attachmentContent.getAsosiyContent()), attachmentContent.getAttachment().getFileOriginalName());
        sendPhoto.setPhoto(inputFile);
        return sendPhoto;
    }

    @Override
    public SendMessage tarjima(Update update) {
      return null;
    }

    public SendMessage adminPanel(Update update) {
         SendMessage sendMessage=new SendMessage();
         sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
         sendMessage.setText("Salom "+update.getMessage().getFrom().getFirstName()+" " + (update.getMessage().getFrom().getLastName() == null ? "" : update.getMessage().getFrom().getLastName()));
         sendMessage.setText("Admin panelga xush kelipsiz!");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2=new KeyboardRow();
        KeyboardButton button1=new KeyboardButton(BotContains.USERS);
        KeyboardButton button2 = new KeyboardButton(BotContains.CRUD_MENU);
        KeyboardButton button3=new KeyboardButton(BotContains.SEND_ACTION);
        row1.add(button1);
        row1.add(button2);
        row2.add(button3);
        keyboardRowList.add(row1);
        keyboardRowList.add(row2);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }
}
