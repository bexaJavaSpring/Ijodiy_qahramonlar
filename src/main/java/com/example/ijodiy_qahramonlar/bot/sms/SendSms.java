package com.example.ijodiy_qahramonlar.bot.sms;

import com.example.ijodiy_qahramonlar.bot.BotConfig;

public class SendSms implements BotConfig, SmsService {
    @Override
    public int codeGenerate() {
        int min = 1000;
        int max = 9999;
        int code = (int) (Math.random() * (max - min + 1) + min);
        return code;
    }
}
