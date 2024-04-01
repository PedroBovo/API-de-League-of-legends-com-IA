package com.pedro.lol.campeoesLol.adapters.out;

import com.pedro.lol.campeoesLol.domain.ports.GenerativeAiApi;
import feign.FeignException;
import feign.RequestInterceptor;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@ConditionalOnProperty(name = "generative-ai.provider", havingValue = "GEMINI")
@FeignClient(name = "geminiApi", url = "https://generativelanguage.googleapis.com", configuration = GoogleGeminiApi.Config.class)
public interface GoogleGeminiApi extends GenerativeAiApi {

    @PostMapping("/v1beta/models/gemini-pro:generateContent")
    GoogleGeminiResponse textOnlyInput(GoogleGeminiRequest request);

    @Override
   default String generateContent(String objective, String context){
        String prompt = """
                %s
                %s
                """.formatted(objective, context);
        GoogleGeminiRequest request = new GoogleGeminiRequest(
          List.of(new Content(List.of(new Part(prompt))))
        );
        try {
            GoogleGeminiResponse response = textOnlyInput(request);
            return response.candidates().get(0).content().parts().get(0).text();
        }catch (FeignException httpError){
            return "Deu ruim, erro de comunicação com a API do google gemini";
        }catch (Exception unexpectedError){
            return "Deu mais ruim ainda, o retorno da API do google gemini não contem os dados esperados";
        }

    }


    record GoogleGeminiRequest(List<Content> contents){ }
    record Content(List<Part> parts){}
    record Part(String text){}

    record GoogleGeminiResponse(List<Candidate> candidates){ }
    record Candidate(Content content){}

    class Config{
        @Bean
        public RequestInterceptor apiKeyRequestInterceptor(@Value("${GEMINI_API}")String apiKey){
            return requestTemplate -> requestTemplate.query("key", apiKey);
        }
    }
}
