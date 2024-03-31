package com.pedro.lol.campeoesLol.adapters.in;

import com.pedro.lol.campeoesLol.application.ListChampionsUseCase;
import com.pedro.lol.campeoesLol.domain.model.Champions;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name= "Campeões", description = "Endpoints do dominio de campeões do LOL")
@RestController
@RequestMapping("/champions")
public record ListChampionsRestController(ListChampionsUseCase useCase) {

    @GetMapping
    public List<Champions> findAllChampions(){
        return useCase.findAll();
    }
}
