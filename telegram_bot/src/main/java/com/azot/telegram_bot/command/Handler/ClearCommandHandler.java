package com.azot.telegram_bot.command.Handler;

import com.azot.telegram_bot.command.TelegramCommandHandler;
import com.azot.telegram_bot.command.TelegramCommands;
import com.azot.telegram_bot.openai.ChatGptHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
@Component
@AllArgsConstructor
public class ClearCommandHandler implements TelegramCommandHandler {

    private final ChatGptHistoryService chatGptHistory;
    private final String CLEAR_HISTORY = """
            История вашего диалога была очищена!
            """;
    @Override
    public BotApiMethod<?> processCommand(Message message) {
        chatGptHistory.clearHistory(message.getChatId());
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(CLEAR_HISTORY)
                .build();
    }

    @Override
    public TelegramCommands getSupportedCommand() {
        return TelegramCommands.CLEAR_COMMAND;
    }
}
