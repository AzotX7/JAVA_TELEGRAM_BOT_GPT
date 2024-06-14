package com.azot.telegram_bot.openai;

import com.azot.telegram_bot.openai.api.ChatCompletionRequest;
import com.azot.telegram_bot.openai.api.Message;
import com.azot.telegram_bot.openai.api.OpenAIClient;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChatGptService {
    private final OpenAIClient openAIClient;
    private final ChatGptHistoryService chatGptHistory;

    @Nonnull
    public String getResponseChatForUser(Long userId,String userTextInput){
        chatGptHistory.createHistoryIfNotExist(userId);

        var history = chatGptHistory.addMessageHistory(userId,Message.builder()
                .content(userTextInput)
                .role("user")
                .build()
        );

        var request = ChatCompletionRequest.builder()
                .model("gpt-4o")
                .messages(history.messages())
                .build();
        var response = openAIClient.createChatCompletion(request);

        var messageFromGpt = response.choices().get(0).message();

        chatGptHistory.addMessageHistory(userId,messageFromGpt);

        return messageFromGpt.content();
    }
}
