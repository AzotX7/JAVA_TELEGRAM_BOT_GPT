package com.azot.telegram_bot.telegram.handler;

import com.azot.telegram_bot.command.TelegramCommandsDispatcher;
import com.azot.telegram_bot.telegram.TelegramAsyncMessageSender;
import com.azot.telegram_bot.telegram.handler.TelegramTextHandler;
import com.azot.telegram_bot.telegram.handler.TelegramVoiceHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
@Slf4j
@Service
@AllArgsConstructor
public class TelegramUpdateMessageHandler {

    private final TelegramCommandsDispatcher telegramCommandsDispatcher;
    private final TelegramAsyncMessageSender telegramAsyncMessageSender;
    private final TelegramTextHandler telegramTextHandler;
    private final TelegramVoiceHandler telegramVoiceHandler;
    public BotApiMethod<?> handleMessage(Message message) {
        if (telegramCommandsDispatcher.isCommand(message)) {
            return telegramCommandsDispatcher.processCommand(message);
        }
        var chatId = message.getChatId().toString();

        if (message.hasVoice() || message.hasText()) {
            telegramAsyncMessageSender.SendMessageAsync(
                    chatId,
                    () -> handleMessageAsync(message),
                    this::getErrorMessage

            );
        }
        return null;

    }

    private SendMessage getErrorMessage(Throwable throwable) {
        log.error("Произошла ошибка",throwable);
        return SendMessage.builder()
                .text("Произошла ошибка, попробуйте позже")
                .build();
    }

    private SendMessage handleMessageAsync(Message message) {
        if(message.hasVoice()){
            return telegramVoiceHandler.processVoice(message);
        }else {
            return telegramTextHandler.processText(message);

        }
    }
}
