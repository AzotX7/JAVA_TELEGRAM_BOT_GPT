package com.azot.telegram_bot.command.Handler;

import com.azot.telegram_bot.command.TelegramCommandHandler;
import com.azot.telegram_bot.command.TelegramCommands;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
@Component
public class StartCommandHandler implements TelegramCommandHandler {
    private final String HELLO_MESSAGE = """
            Привет, дорогой друг %s,
            Этот бот абсолютно бесплатен, юзай наздоровье.
            Очистить контекст можно с помощью команды /clear
            """;
    @Override
    public BotApiMethod<?> processCommand(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(HELLO_MESSAGE.formatted(message.getChat().getFirstName()))
                .build();
    }

    @Override
    public TelegramCommands getSupportedCommand() {
        return TelegramCommands.START_COMMAND;
    }
}
