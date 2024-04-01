package com.pedro.lol.campeoesLol.adapters.out;

import com.pedro.lol.campeoesLol.domain.ports.GenerativeAiApi;
import feign.FeignException;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@ConditionalOnProperty(name = "generative-ai.provider", havingValue = "OPENAI", matchIfMissing = true)
@FeignClient(name = "openAiApi", url = "https://api.openai.com", configuration = OpenAiChatApi.Config.class)
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

        try {
            OpenAiChatCompletionResponse resp =  chatCompletion(req);
            return resp.choices().get(0).message().content();
        }catch (FeignException httpError){
            return "Deu ruim, erro de comunicação com a API da OpenAI";
        }catch (Exception unexpectedError){
            return "Deu mais ruim ainda, o retorno da API da OpenAI não contem os dados esperados";
        }
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
