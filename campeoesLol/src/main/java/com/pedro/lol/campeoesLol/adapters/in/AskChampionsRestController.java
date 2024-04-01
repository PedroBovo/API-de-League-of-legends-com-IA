package com.pedro.lol.campeoesLol.adapters.in;

import com.pedro.lol.campeoesLol.application.AskChampionUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;


@Tag(name= "Campeões", description = "Endpoints do dominio de campeões do LOL")
@RestController
@RequestMapping("/champions")
public record AskChampionsRestController(AskChampionUseCase useCase) {

    @CrossOrigin
    @PostMapping("/{championId}/ask")
    public AskChampionResponse askChampions(@PathVariable Long championId,
                                            @RequestBody AskChampionRequest request){
        String answer = useCase.askChampion(championId, request.question());
        return new AskChampionResponse(answer) ;
    }

    public record AskChampionRequest(String question){}
    public record AskChampionResponse(String answer){}
}
