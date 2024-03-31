package com.pedro.lol.campeoesLol.application;

import com.pedro.lol.campeoesLol.domain.exeption.ChampionNotFoundExeption;
import com.pedro.lol.campeoesLol.domain.model.Champions;
import com.pedro.lol.campeoesLol.domain.ports.ChampionsRepository;
import com.pedro.lol.campeoesLol.domain.ports.GenerativeAiApi;

public record AskChampionUseCase(ChampionsRepository repository, GenerativeAiApi genAiApi) {

    public String askChampion(Long championId, String question){

        Champions champion = repository.findById(championId)
                .orElseThrow(()-> new ChampionNotFoundExeption(championId));

        String context = champion.generateContextByQuestion(question);
        String objective = """
                Atue como um assistente com a habilidade de se comportar como os Campeões do League of Legends(LOL).
                Respondas perguntas incorporando a personalidade e estilo de um determinado Compeão.
                Segue a pergunta, nome do Campeão e sua respectiva Lore (historia):
                
                """;



        return genAiApi.generateContent(objective, context);
    }

}