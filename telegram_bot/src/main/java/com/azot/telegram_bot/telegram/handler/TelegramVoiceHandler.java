package com.azot.telegram_bot.telegram.handler;

import com.azot.telegram_bot.openai.ChatGptService;
import com.azot.telegram_bot.openai.TranscribeVoiceToTextService;
import com.azot.telegram_bot.telegram.TelegramFileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
@Service
@AllArgsConstructor
public class TelegramVoiceHandler {
    private final TelegramFileService telegramFileService;
    private final TranscribeVoiceToTextService transcribeVoiceToTextService;
    private final ChatGptService chatGptService;
    public SendMessage processVoice(Message message) {
        var voice = message.getVoice();
        var fileId = voice.getFileId();
        var file = telegramFileService.getFile(fileId);

        var chatId = message.getChatId();

        var text = transcribeVoiceToTextService.transcribe(file);

        var gptGeneratedText = chatGptService.getResponseChatForUser(chatId,text);

        return new SendMessage(chatId.toString(),gptGeneratedText);
    }
}
