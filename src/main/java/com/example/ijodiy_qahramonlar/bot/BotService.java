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
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class BotService implements BotServiceImpl {
    final UserRepository userRepository;
    final RegionRepository regionRepository;
    final AttachmentRepository attachmentRepository;
    final AttachmentContentRepository attachmentContentRepository;
    final CategoryRepository categoryRepository;
    final AuthorRepository authorRepository;
    final PoemRepository poemRepository;
    final PostRepository postRepository;
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
        KeyboardButton button1 = new KeyboardButton(BotContains.CATEGORIES);
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
        SendMessage sendMessage = new SendMessage();
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
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText("G'oya muallifi: Begzodbek" + "\n" + "Call Center: (93) 620-75-16" + "\n" + "Yordam berganlar: Quddus aka");
        return sendMessage;
    }

    @Override
    public SendPhoto selectRegion(Update update) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(String.valueOf(update.getMessage().getChatId()));
        Optional<Region> optionalRegion = regionRepository.findByName(update.getMessage().getText());
        Region region = null;
        if (optionalRegion.isPresent()) {
            region = optionalRegion.get();
        }
        Attachment attachment = region.getAttachment();

        Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(attachment.getId());
        AttachmentContent attachmentContent = null;
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
        Category category = null;
        if (optionalCategory.isPresent()) {
            category = optionalCategory.get();
        }
        Attachment attachment = category.getAttachment();

        Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(attachment.getId());
        AttachmentContent attachmentContent = null;
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

        String regionName = update.getMessage().getText();
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
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        String authorName = update.getMessage().getText();
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
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        String poemName = update.getMessage().getText();
        Optional<Poem> all = poemRepository.findByName(poemName);
        Poem poem1 = all.get();
        sendMessage.setText(poem1.getDescription() + "\n" + "t.me/creative_you_bot");


        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        return sendMessage;
    }

    @SneakyThrows
    @Override
    public SendDocument UserList(Update update) {
        SendDocument sendDocument=new SendDocument();
        sendDocument.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendDocument.setCaption("Mana marxamat");
        String path = "src/main/resources/";

        File userPDF = new File(path + "users.pdf");

        FileOutputStream outputStream = new FileOutputStream(userPDF);

        PdfWriter pdfWriter = new PdfWriter(outputStream);

        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);

        pdfDocument.addNewPage();
        Paragraph paragraph = new Paragraph("All bot users ");
        paragraph.setBold();
        paragraph.setFontSize(22);
        paragraph.setTextAlignment(TextAlignment.CENTER);

        document.add(paragraph);

        Table table = new Table(5);

        table.addCell("T/r ");
        table.addCell("Chat id");
        table.addCell("Full name");
        table.addCell("Username");
        table.addCell("Step");


        Integer number = 1;

        List<User> all = userRepository.findAll();

        for (User user : all) {
            table.addCell((number++) + "");
            table.addCell(String.valueOf(user.getChatId()));
            table.addCell(user.getFirstName() + user.getLastName());
            table.addCell(user.getUsername());
            table.addCell(user.getStep());
        }

        Paragraph paragraph1 = new Paragraph("All bot users count :" + all.size());

        document.add(table);
        document.add(paragraph1);
        pdfDocument.close();
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendDocument.setDocument(new InputFile(userPDF));
        return sendDocument;
    }

    @Override
    public SendMessage crudMenu(Update update) {
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText("Menu");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        row1.add(BotContains.CATEGORY_CRUD);
        row1.add(BotContains.REGION_CRUD);
        row1.add(BotContains.AUTHOR_CRUD);
        row2.add(BotContains.POEM_CRUD);
        row2.add(BotContains.POST_CRUD);
        row2.add(BotContains.ATTACHMENT_CRUD);
        keyboardRowList.add(row1);
        keyboardRowList.add(row2);
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
    public SendPhoto sendNotification(Long chatId, Post post) {
        Attachment attachment = post.getAttachment();
        Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(attachment.getId());
        AttachmentContent attachmentContent = null;
        if (optionalAttachmentContent.isPresent()) {
            attachmentContent = optionalAttachmentContent.get();
        }
        InputFile inputFile = new InputFile(new ByteArrayInputStream(attachmentContent.getAsosiyContent()), attachmentContent.getAttachment().getFileOriginalName());
        StringBuilder stringBuilder=new StringBuilder();
        StringBuilder Reklama = stringBuilder
                .append(post.getDescription() + "\n");
        return SendPhoto.builder().chatId(String.valueOf(chatId)).caption(String.valueOf(Reklama)).photo(inputFile).build();
    }

//    public SendMessage categoryCrud(Update update){
//        SendMessage sendMessage=new SendMessage();
//        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
//        sendMessage.setText("Tanlang:");
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(true);
//        replyKeyboardMarkup.setSelective(true);
//
//        List<KeyboardRow> keyboardRowList = new ArrayList<>();
//        KeyboardRow row4 = new KeyboardRow();
//        KeyboardRow row5 = new KeyboardRow();
//        row4.add(BotContains.CATA);
//        row4.add(BotContains.CATU);
//        row5.add(BotContains.CATD);
//        row5.add(BotContains.CATL);
//        keyboardRowList.add(row4);
//        keyboardRowList.add(row5);
//        KeyboardRow row = new KeyboardRow();
//        KeyboardButton back = new KeyboardButton(BotContains.BACK);
//        KeyboardButton mainMenu = new KeyboardButton(BotContains.MAIN_MENU_BUTTON);
//        row.add(back);
//        row.add(mainMenu);
//        keyboardRowList.add(row);
//        replyKeyboardMarkup.setKeyboard(keyboardRowList);
//        sendMessage.setReplyMarkup(replyKeyboardMarkup);
//        return sendMessage;
//    }

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
        if (author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId() == 1) {
            builder.append("\uD83D\uDCCC" + author.getFullName() + " ")
                    .append(author.getBirthDate() + " yilda ")
                    .append("Andijon viloyatida tug'ilgan.")
                    .append("\n");
        }
        if (author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId() == 2) {
            builder.append("\uD83D\uDCCC" + author.getFullName() + " ")
                    .append(author.getBirthDate() + " yilda ")
                    .append("Buxoro viloyatida tug'ilgan.")
                    .append("\n");
        }
        if (author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId() == 3) {
            builder.append("\uD83D\uDCCC" + author.getFullName() + " ")
                    .append(author.getBirthDate() + " yilda ")
                    .append("Farg'ona viloyatida tug'ilgan.")
                    .append("\n");
        }
        if (author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId() == 4) {
            builder.append("\uD83D\uDCCC" + author.getFullName() + " ")
                    .append(author.getBirthDate() + " yilda ")
                    .append("Jizzax viloyatida tug'ilgan.")
                    .append("\n");
        }
        if (author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId() == 5) {
            builder.append("\uD83D\uDCCC" + author.getFullName() + " ")
                    .append(author.getBirthDate() + " yilda ")
                    .append("Namangan viloyatida tug'ilgan.")
                    .append("\n");
        }
        if (author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId() == 6) {
            builder.append("\uD83D\uDCCC" + author.getFullName() + " ")
                    .append(author.getBirthDate() + " yilda ")
                    .append("Navoiy viloyatida tug'ilgan.")
                    .append("\n");
        }
        if (author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId() == 7) {
            builder.append("\uD83D\uDCCC" + author.getFullName() + " ")
                    .append(author.getBirthDate() + " yilda ")
                    .append("Qashqadaryo viloyatida tug'ilgan.")
                    .append("\n");
        }
        if (author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId() == 8) {
            builder.append("\uD83D\uDCCC" + author.getFullName() + " ")
                    .append(author.getBirthDate() + " yilda ")
                    .append("Qoraqalpog'iston viloyatida tug'ilgan.")
                    .append("\n");
        }
        if (author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId() == 9) {
            builder.append("\uD83D\uDCCC" + author.getFullName() + " ")
                    .append(author.getBirthDate() + " yilda ")
                    .append("Samarqand viloyatida tug'ilgan.")
                    .append("\n");
        }
        if (author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId() == 10) {
            builder.append("\uD83D\uDCCC" + author.getFullName() + " ")
                    .append(author.getBirthDate() + " yilda ")
                    .append("Sirdaryo viloyatida tug'ilgan.")
                    .append("\n");
        }
        if (author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId() == 11) {
            builder.append("\uD83D\uDCCC" + author.getFullName() + " ")
                    .append(author.getBirthDate() + " yilda ")
                    .append("Surxondaryo viloyatida tug'ilgan.")
                    .append("\n");
        }
        if (author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId() == 12) {
            builder.append("\uD83D\uDCCC" + author.getFullName() + " ")
                    .append(author.getBirthDate() + " yilda ")
                    .append("Toshkent viloyatida tug'ilgan.")
                    .append("\n");
        }
        if (author.getDistrict().equals(" ") && author.getVillage().equals(" ") && author.getRegion().getId() == 13) {
            builder.append("\uD83D\uDCCC" + author.getFullName() + " ")
                    .append(author.getBirthDate() + " yilda ")
                    .append("Xorazm viloyatida tug'ilgan.")
                    .append("\n");
        }
        builder.append("\uD83D\uDCCC" + author.getFullName() + " ")
                .append(author.getBirthDate() + " yilda ")
                .append(author.getRegions() + " viloyati, ")
                .append(author.getDistrict() + " tumani ")
                .append(author.getVillage()+" qishlog'ida tug'ilgan.")
                .append("\n");
        sendPhoto.setCaption(String.valueOf(builder));
        InputFile inputFile = new InputFile(new ByteArrayInputStream(attachmentContent.getAsosiyContent()), attachmentContent.getAttachment().getFileOriginalName());
        sendPhoto.setPhoto(inputFile);
        return sendPhoto;
    }

    @Override
    public SendMessage adminToMessage(Update update) {
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText("Hozircha bu qismi ishlamaydi");
        return sendMessage;
    }
    @Override
    public SendMessage tarjima(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText("Hozicha bu joy ishlamaydi");
        return sendMessage;
    }

    public SendMessage adminPanel(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText("Salom " + update.getMessage().getFrom().getFirstName() + " " + (update.getMessage().getFrom().getLastName() == null ? "" : update.getMessage().getFrom().getLastName()));
        sendMessage.setText("Admin panelga xush kelipsiz!");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardButton button1 = new KeyboardButton(BotContains.USERS);
        KeyboardButton button2 = new KeyboardButton(BotContains.CRUD_MENU);
        KeyboardButton button3 = new KeyboardButton(BotContains.SEND_ACTION);
        row1.add(button1);
        row1.add(button2);
        row2.add(button3);
        keyboardRowList.add(row1);
        keyboardRowList.add(row2);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

//
//    public StringBuilder categoryList() {
//        StringBuilder categories = new StringBuilder();
//        int i=1;
//        for (Category category : categoryRepository.findAll()) {
//            categories.append("<b>"+(i++)+". "+category.getName()+"</b>\n");
//        }
//        return categories;
//    }
//
//    public StringBuilder regionList() {
//        StringBuilder regions = new StringBuilder();
//        int i = 1;
//        for (Region region : regionRepository.findAll()) {
//            regions.append("<b>"+(i++)+". "+region.getName()+"</b>\n");
//        }
//        return regions;
//    }
//
//    public StringBuilder authorList() {
//        StringBuilder authors = new StringBuilder();
//        int i = 1;
//        for (Author author : authorRepository.findAll()) {
//            authors.append("<b>" +(i++)+". "+ author.getFullName() + "</b>\n");
//        }
//        return authors;
//    }
//
//    public StringBuilder poemList() {
//        StringBuilder poems = new StringBuilder();
//        int i = 1;
//        for (Poem poem : poemRepository.findAll()) {
//            poems.append("<b>" +(i++)+". "+ poem.getName() + "</b>\n");
//        }
//        return poems;
//    }
//
//
//    public SendMessage regionCrud(Update update) {
//        SendMessage sendMessage=new SendMessage();
//        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
//        sendMessage.setText("Tanlang:");
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(true);
//        replyKeyboardMarkup.setSelective(true);
//
//        List<KeyboardRow> keyboardRowList = new ArrayList<>();
//        KeyboardRow row4 = new KeyboardRow();
//        KeyboardRow row5 = new KeyboardRow();
//        row4.add(BotContains.REGA);
//        row4.add(BotContains.REGU);
//        row5.add(BotContains.REGD);
//        row5.add(BotContains.REGL);
//        keyboardRowList.add(row4);
//        keyboardRowList.add(row5);
//        KeyboardRow row = new KeyboardRow();
//        KeyboardButton back = new KeyboardButton(BotContains.BACK);
//        KeyboardButton mainMenu = new KeyboardButton(BotContains.MAIN_MENU_BUTTON);
//        row.add(back);
//        row.add(mainMenu);
//        keyboardRowList.add(row);
//        replyKeyboardMarkup.setKeyboard(keyboardRowList);
//        sendMessage.setReplyMarkup(replyKeyboardMarkup);
//        return sendMessage;
//    }
//
//    public SendMessage authorCrud(Update update) {
//        SendMessage sendMessage=new SendMessage();
//        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
//        sendMessage.setText("Tanlang:");
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(true);
//        replyKeyboardMarkup.setSelective(true);
//
//        List<KeyboardRow> keyboardRowList = new ArrayList<>();
//        KeyboardRow row4 = new KeyboardRow();
//        KeyboardRow row5 = new KeyboardRow();
//        row4.add(BotContains.AUTA);
//        row4.add(BotContains.AUTU);
//        row5.add(BotContains.AUTD);
//        row5.add(BotContains.AUTL);
//        keyboardRowList.add(row4);
//        keyboardRowList.add(row5);
//        KeyboardRow row = new KeyboardRow();
//        KeyboardButton back = new KeyboardButton(BotContains.BACK);
//        KeyboardButton mainMenu = new KeyboardButton(BotContains.MAIN_MENU_BUTTON);
//        row.add(back);
//        row.add(mainMenu);
//        keyboardRowList.add(row);
//        replyKeyboardMarkup.setKeyboard(keyboardRowList);
//        sendMessage.setReplyMarkup(replyKeyboardMarkup);
//        return sendMessage;
//    }
//
//    public SendMessage poemCrud(Update update) {
//        SendMessage sendMessage=new SendMessage();
//        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
//        sendMessage.setText("Tanlang:");
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(true);
//        replyKeyboardMarkup.setSelective(true);
//
//        List<KeyboardRow> keyboardRowList = new ArrayList<>();
//        KeyboardRow row4 = new KeyboardRow();
//        KeyboardRow row5 = new KeyboardRow();
//        row4.add(BotContains.PA);
//        row4.add(BotContains.PU);
//        row5.add(BotContains.PL);
//        row5.add(BotContains.PD);
//        keyboardRowList.add(row4);
//        keyboardRowList.add(row5);
//        KeyboardRow row = new KeyboardRow();
//        KeyboardButton back = new KeyboardButton(BotContains.BACK);
//        KeyboardButton mainMenu = new KeyboardButton(BotContains.MAIN_MENU_BUTTON);
//        row.add(back);
//        row.add(mainMenu);
//        keyboardRowList.add(row);
//        replyKeyboardMarkup.setKeyboard(keyboardRowList);
//        sendMessage.setReplyMarkup(replyKeyboardMarkup);
//        return sendMessage;
//    }
//
//    public SendMessage postCrud(Update update) {
//        SendMessage sendMessage=new SendMessage();
//        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
//        sendMessage.setText("Tanlang:");
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(true);
//        replyKeyboardMarkup.setSelective(true);
//
//        List<KeyboardRow> keyboardRowList = new ArrayList<>();
//        KeyboardRow row4 = new KeyboardRow();
//        KeyboardRow row5 = new KeyboardRow();
//        row4.add(BotContains.POA);
//        row4.add(BotContains.POU);
//        row5.add(BotContains.POL);
//        row5.add(BotContains.POD);
//        keyboardRowList.add(row4);
//        keyboardRowList.add(row5);
//        KeyboardRow row = new KeyboardRow();
//        KeyboardButton back = new KeyboardButton(BotContains.BACK);
//        KeyboardButton mainMenu = new KeyboardButton(BotContains.MAIN_MENU_BUTTON);
//        row.add(back);
//        row.add(mainMenu);
//        keyboardRowList.add(row);
//        replyKeyboardMarkup.setKeyboard(keyboardRowList);
//        sendMessage.setReplyMarkup(replyKeyboardMarkup);
//        return sendMessage;
//    }
//
//    public SendMessage attachmentCrud(Update update) {
//        SendMessage sendMessage=new SendMessage();
//        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
//        sendMessage.setText("Tanlang:");
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(true);
//        replyKeyboardMarkup.setSelective(true);
//
//        List<KeyboardRow> keyboardRowList = new ArrayList<>();
//        KeyboardRow row4 = new KeyboardRow();
//        KeyboardRow row5 = new KeyboardRow();
//        row4.add(BotContains.AA);
//        row4.add(BotContains.AU);
//        row5.add(BotContains.AL);
//        row5.add(BotContains.AD);
//        keyboardRowList.add(row4);
//        keyboardRowList.add(row5);
//        KeyboardRow row = new KeyboardRow();
//        KeyboardButton back = new KeyboardButton(BotContains.BACK);
//        KeyboardButton mainMenu = new KeyboardButton(BotContains.MAIN_MENU_BUTTON);
//        row.add(back);
//        row.add(mainMenu);
//        keyboardRowList.add(row);
//        replyKeyboardMarkup.setKeyboard(keyboardRowList);
//        sendMessage.setReplyMarkup(replyKeyboardMarkup);
//        return sendMessage;
//    }
//
//    public InlineKeyboardMarkup updateCategory(Update update) {
//
//        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> inlineButtons = new ArrayList<>();
//        List<InlineKeyboardButton> buttonList = new ArrayList<>();
//        inlineButtons.add(buttonList);
//        inlineKeyboardMarkup.setKeyboard(inlineButtons);
//
//
//        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
//        List<KeyboardRow> rowList = new ArrayList<>();
//
//        keyboardMarkup.setKeyboard(rowList);
//        keyboardMarkup.setResizeKeyboard(true);
//        keyboardMarkup.setOneTimeKeyboard(true);
//        List<InlineKeyboardButton> inlineRow = new ArrayList<>();
//        List<InlineKeyboardButton> inlinePrev = new ArrayList<>();
//        for (int i = 0; i < categoryRepository.findAll().size(); i++) {
//            Category value = categoryRepository.getById(i);
//
//            InlineKeyboardButton buttonN = new InlineKeyboardButton();
//            buttonN.setText(value.getName());
//            buttonN.setCallbackData(value.getName());
//            inlineRow.add(buttonN);
//
//            if (i % 2 == 0) {
//                inlineButtons.add(inlineRow);
//            } else {
//                inlineRow = new ArrayList<>();
//            }
//        }
//        inlineButtons.add(inlinePrev);
//        return inlineKeyboardMarkup;
//    }
//
//    public SendMessage chooseRegion(Update update) {
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
//
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(true);
//        replyKeyboardMarkup.setSelective(true);
//
//        List<KeyboardRow> keyboardRowList = new ArrayList<>();
//
//        List<Region> all = regionRepository.findAll();
//        int size = all.size();
//        int r = (size % 2 == 1) ? size - 1 : size;
//        for (int i = 0; i < r; i = i + 2) {
//            KeyboardRow row1 = new KeyboardRow();
//            KeyboardButton button = new KeyboardButton(all.get(i).getName());
//            KeyboardButton button1 = new KeyboardButton(all.get(i + 1).getName());
//            row1.add(button);
//            row1.add(button1);
//            keyboardRowList.add(row1);
//        }
//        if (size % 2 == 1) {
//            KeyboardRow row = new KeyboardRow();
//            KeyboardButton keyboardButton = new KeyboardButton();
//            keyboardButton.setText(all.get(size - 1).getName());
//            row.add(keyboardButton);
//            keyboardRowList.add(row);
//        }
//
//        replyKeyboardMarkup.setKeyboard(keyboardRowList);
//        sendMessage.setReplyMarkup(replyKeyboardMarkup);
//        return sendMessage;
//    }
//
//    public ReplyKeyboard updateRegion(Update update) {
//        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> inlineButtons = new ArrayList<>();
//        List<InlineKeyboardButton> buttonList = new ArrayList<>();
//        inlineButtons.add(buttonList);
//        inlineKeyboardMarkup.setKeyboard(inlineButtons);
//
//
//        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
//        List<KeyboardRow> rowList = new ArrayList<>();
//
//        keyboardMarkup.setKeyboard(rowList);
//        keyboardMarkup.setResizeKeyboard(true);
//        keyboardMarkup.setOneTimeKeyboard(true);
//        List<InlineKeyboardButton> inlineRow = new ArrayList<>();
//        List<InlineKeyboardButton> inlinePrev = new ArrayList<>();
//        for (int i = 0; i < regionRepository.findAll().size(); i++) {
//            Region value = regionRepository.getById(i);
//
//            InlineKeyboardButton buttonN = new InlineKeyboardButton();
//            buttonN.setText(value.getName());
//            buttonN.setCallbackData(value.getName());
//            inlineRow.add(buttonN);
//
//            if (i % 2 == 0) {
//                inlineButtons.add(inlineRow);
//            } else {
//                inlineRow = new ArrayList<>();
//            }
//        }
//        InlineKeyboardButton buttonH = new InlineKeyboardButton();
//        buttonH.setText("Back");
//        buttonH.setCallbackData("Back");
//        inlinePrev.add(buttonH);
//        inlineButtons.add(inlinePrev);
//        return inlineKeyboardMarkup;
//    }
//
//    public ReplyKeyboard updateAuthor(Update update) {
//        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> inlineButtons = new ArrayList<>();
//        List<InlineKeyboardButton> buttonList = new ArrayList<>();
//        inlineButtons.add(buttonList);
//        inlineKeyboardMarkup.setKeyboard(inlineButtons);
//
//
//        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
//        List<KeyboardRow> rowList = new ArrayList<>();
//
//        keyboardMarkup.setKeyboard(rowList);
//        keyboardMarkup.setResizeKeyboard(true);
//        keyboardMarkup.setOneTimeKeyboard(true);
//        List<InlineKeyboardButton> inlineRow = new ArrayList<>();
//        List<InlineKeyboardButton> inlinePrev = new ArrayList<>();
//        for (int i = 0; i < authorRepository.findAll().size(); i++) {
//            Author value = authorRepository.getById(i);
//
//            InlineKeyboardButton buttonN = new InlineKeyboardButton();
//            buttonN.setText(value.getFullName());
//            buttonN.setCallbackData(value.getFullName());
//            inlineRow.add(buttonN);
//
//            if (i % 2 == 0) {
//                inlineButtons.add(inlineRow);
//            } else {
//                inlineRow = new ArrayList<>();
//            }
//        }
//        InlineKeyboardButton buttonH = new InlineKeyboardButton();
//        buttonH.setText("Back");
//        buttonH.setCallbackData("Back");
//        inlinePrev.add(buttonH);
//        inlineButtons.add(inlinePrev);
//        return inlineKeyboardMarkup;
//    }
}
