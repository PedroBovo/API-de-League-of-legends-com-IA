package com.pedro.lol.campeoesLol.adapters.out;

import com.pedro.lol.campeoesLol.domain.ports.GenerativeAiApi;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "openAiChatApi", url = "https://api.openai.com", configuration = OpenAiChatApi.Config.class)
public interface OpenAiChatApi extends GenerativeAiApi {

    @PostMapping("/v1/chat/completions")
    OpenAiChatCompletionResponse chatCompletion(OpenAiChatCompletionRequest request);

    @Override
   default String generateContent(String objective, String context){
        String model = "gpt-3.5-turbo";
        List<Message> messages = List.of(
                new Message("system", objective),
                new Message("user", context)
        );
        OpenAiChatCompletionRequest req = new OpenAiChatCompletionRequest(model, messages);

       OpenAiChatCompletionResponse resp =  chatCompletion(req);

        return resp.choices().get(0).message().content();
    }

    record OpenAiChatCompletionRequest(String model, List<Message> messages){ }
     record Message(String role, String content){ }

    record OpenAiChatCompletionResponse(List<Choice> choices){ }
    record Choice(Message message){ }

    class Config{
        @Bean
        public RequestInterceptor apiKeyRequestInterceptor(@Value("${OPENAI_API_KEY}")String apiKey){
            return requestTemplate -> requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(apiKey));
        }
    }
}
