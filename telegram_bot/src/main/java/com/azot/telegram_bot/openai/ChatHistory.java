package com.azot.telegram_bot.openai;
import com.azot.telegram_bot.openai.api.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;
@Builder
public record ChatHistory(

        List<Message> messages
) {
}
