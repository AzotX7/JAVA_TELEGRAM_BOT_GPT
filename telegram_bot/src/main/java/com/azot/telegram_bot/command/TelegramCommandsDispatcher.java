package com.azot.telegram_bot.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
@Service
@AllArgsConstructor
public class TelegramCommandsDispatcher {
    private final List<TelegramCommandHandler> telegramCommandHandlerList;

    public BotApiMethod<?> processCommand(Message message){
        if(!isCommand(message)){
            throw new IllegalArgumentException("Not a command passed");
        }
        var text = message.getText();
        var command = telegramCommandHandlerList.stream()
                .filter(it -> it.getSupportedCommand().getCommandValue().equals(text))
                .findAny();
        if(command.isEmpty()){
            return SendMessage.builder()
                    .chatId(message.getChatId())
                    .text("Not supported command: command=%s".formatted(text))
                    .build();
        }
        return command.orElseThrow().processCommand(message);
    }


    public boolean isCommand(Message message) {
        return message.hasText() &&
                message.getText().startsWith("/");
    }
}
