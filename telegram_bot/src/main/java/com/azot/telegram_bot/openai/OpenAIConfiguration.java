package com.azot.telegram_bot.openai;

import com.azot.telegram_bot.openai.api.OpenAIClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIConfiguration {
    @Bean
    public OpenAIClient openAIClient(@Value("${openai.token}") String botToken, RestTemplateBuilder restTemplate){
        return new OpenAIClient(botToken,restTemplate.build());
    }
}
