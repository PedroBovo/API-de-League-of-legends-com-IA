package com.pedro.lol.campeoesLol;

import com.pedro.lol.campeoesLol.application.AskChampionUseCase;
import com.pedro.lol.campeoesLol.application.ListChampionsUseCase;
import com.pedro.lol.campeoesLol.domain.ports.ChampionsRepository;
import com.pedro.lol.campeoesLol.domain.ports.GenerativeAiApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@SpringBootApplication
public class CampeoesLolApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampeoesLolApplication.class, args);
	}

	@Bean
	public ListChampionsUseCase proviedListChampionUseCase(ChampionsRepository repository){
		return new ListChampionsUseCase(repository);
	}
	@Bean
	public AskChampionUseCase proviedAskChampionUseCase(ChampionsRepository repository, GenerativeAiApi genAiApi){
		return new AskChampionUseCase(repository, genAiApi);
	}
}
