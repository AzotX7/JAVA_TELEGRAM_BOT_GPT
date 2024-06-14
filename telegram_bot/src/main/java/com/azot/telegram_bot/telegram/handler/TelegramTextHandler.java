package com.azot.telegram_bot.telegram.handler;

import com.azot.telegram_bot.openai.ChatGptService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
@Service
@AllArgsConstructor
public class TelegramTextHandler {
    private final ChatGptService chatGptService;
    public SendMessage processText(Message message) {
        var text = message.getText();
        var chatId = message.getChatId();

        var gptGeneratedText = chatGptService.getResponseChatForUser(chatId,text);

        return new SendMessage(chatId.toString(),gptGeneratedText);
    }
}
