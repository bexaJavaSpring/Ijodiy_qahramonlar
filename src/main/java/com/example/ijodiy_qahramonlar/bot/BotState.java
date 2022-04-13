package com.example.ijodiy_qahramonlar.bot;

public interface BotState {
    String START = "start";
    String MENU = "menu";
    String SELECT_CAT = "select_category";
    String SELECT_REGION = "select_region";
    String SELECT_AUTHOR = "select_author";
    String SELECT_POEM = "select_poem";

    String FINISH = "yana sherlaridan tanlaymizmi?";
    String SHOW_USER = "Tizimdagi Userlar";
}
