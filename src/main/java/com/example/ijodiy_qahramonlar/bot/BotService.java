package com.example.ijodiy_qahramonlar.bot;

import com.example.ijodiy_qahramonlar.entity.*;
import com.example.ijodiy_qahramonlar.repository.*;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
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
        KeyboardButton button2 = new KeyboardButton(BotContains.Loyiha_mualliflari);
        KeyboardButton button3 = new KeyboardButton(BotContains.TARJIMA);
        KeyboardButton button4 = new KeyboardButton(BotContains.admintoMessage);
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
        sendMessage.setText(poem1.getDescription()+"\n"+"t.me/creative_you_bot");
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

    @SneakyThrows
    @Override
    public SendDocument showUser(Update update) {  // hama userlarning ro'yhatini pdfda chiqarish

       String path = "src/main/resources/";

        File userPDF = new File(path + "users.pdf");



        FileOutputStream outputStream = new FileOutputStream(userPDF);

        PdfWriter pdfWriter = new PdfWriter(outputStream);

        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);

        pdfDocument.addNewPage();
        Paragraph paragraph = new Paragraph("All bot users " );
        paragraph.setBold();
        paragraph.setFontSize(22);
        paragraph.setTextAlignment(TextAlignment.CENTER);

        document.add(paragraph);

        Table table = new Table(5);

        table.addCell("T/r ");
        table.addCell("Chat id");
        table.addCell("Full name");
        table.addCell("Phone number");
        table.addCell("username");


        Integer number = 1;

        List<User> all = userRepository.findAll();

        for (User user : all) {
            table.addCell(number++ +"");
            table.addCell(user.getChatId());
            table.addCell(user.getFullName());
            table.addCell(user.getPhoneNumber());
            table.addCell(user.getUsername());
        }

        Paragraph paragraph1 = new Paragraph("All bot users count :"+ all.size());

        document.add(table);
        document.add(paragraph1);

        pdfDocument.close();

        SendDocument sendDocument = new SendDocument();
        sendDocument.setDocument(new InputFile(userPDF));

        return sendDocument;



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
        if(author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId()==1){
            builder.append("\uD83D\uDCCC"+author.getFullName()+" ")
                    .append(author.getBirthDate()+" yilda ")
                    .append("Andijon viloyatida tug'ilgan.")
                    .append("\n");
        }
        if(author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId()==2){
            builder.append("\uD83D\uDCCC"+author.getFullName()+" ")
                    .append(author.getBirthDate()+" yilda ")
                    .append("Buxoro viloyatida tug'ilgan.")
                    .append("\n");
        }
        if(author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId()==3){
            builder.append("\uD83D\uDCCC"+author.getFullName()+" ")
                    .append(author.getBirthDate()+" yilda ")
                    .append("Farg'ona viloyatida tug'ilgan.")
                    .append("\n");
        }
        if(author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId()==4){
            builder.append("\uD83D\uDCCC"+author.getFullName()+" ")
                    .append(author.getBirthDate()+" yilda ")
                    .append("Jizzax viloyatida tug'ilgan.")
                    .append("\n");
        }
        if(author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId()==5){
            builder.append("\uD83D\uDCCC"+author.getFullName()+" ")
                    .append(author.getBirthDate()+" yilda ")
                    .append("Namangan viloyatida tug'ilgan.")
                    .append("\n");
        }
        if(author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId()==6){
            builder.append("\uD83D\uDCCC"+author.getFullName()+" ")
                    .append(author.getBirthDate()+" yilda ")
                    .append("Navoiy viloyatida tug'ilgan.")
                    .append("\n");
        }
        if(author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId()==7){
            builder.append("\uD83D\uDCCC"+author.getFullName()+" ")
                    .append(author.getBirthDate()+" yilda ")
                    .append("Qashqadaryo viloyatida tug'ilgan.")
                    .append("\n");
        }
        if(author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId()==8){
            builder.append("\uD83D\uDCCC"+author.getFullName()+" ")
                    .append(author.getBirthDate()+" yilda ")
                    .append("Qoraqalpog'iston viloyatida tug'ilgan.")
                    .append("\n");
        }
        if(author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId()==9){
            builder.append("\uD83D\uDCCC"+author.getFullName()+" ")
                    .append(author.getBirthDate()+" yilda ")
                    .append("Samarqand viloyatida tug'ilgan.")
                    .append("\n");
        }
        if(author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId()==10){
            builder.append("\uD83D\uDCCC"+author.getFullName()+" ")
                    .append(author.getBirthDate()+" yilda ")
                    .append("Sirdaryo viloyatida tug'ilgan.")
                    .append("\n");
        }
        if(author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId()==11){
            builder.append("\uD83D\uDCCC"+author.getFullName()+" ")
                    .append(author.getBirthDate()+" yilda ")
                    .append("Surxondaryo viloyatida tug'ilgan.")
                    .append("\n");
        }
        if(author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId()==12){
            builder.append("\uD83D\uDCCC"+author.getFullName()+" ")
                    .append(author.getBirthDate()+" yilda ")
                    .append("Toshkent viloyatida tug'ilgan.")
                    .append("\n");
        }
        if(author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId()==13){
            builder.append("\uD83D\uDCCC"+author.getFullName()+" ")
                    .append(author.getBirthDate()+" yilda ")
                    .append("Xorazm viloyatida tug'ilgan.")
                    .append("\n");
        }
        builder.append("\uD83D\uDCCC"+author.getFullName()+" ")
                .append(author.getBirthDate()+" yilda ")
                .append(author.getRegions()+" viloyati, ")
                .append(author.getDistrict()+" tumani ")
                .append(author.getVillage().equals(" ")?"tug'ilgan.":" qishlog'ida tug'ilgan.")
                .append("\n");
        sendPhoto.setCaption(String.valueOf(builder));
        InputFile inputFile = new InputFile(new ByteArrayInputStream(attachmentContent.getAsosiyContent()), attachmentContent.getAttachment().getFileOriginalName());
        sendPhoto.setPhoto(inputFile);
        return sendPhoto;
    }

    @Override
    public SendMessage adminToMessage(Update update) {
        return null;
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
