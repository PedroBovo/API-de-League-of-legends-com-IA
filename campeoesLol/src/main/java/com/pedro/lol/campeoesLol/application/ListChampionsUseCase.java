package com.pedro.lol.campeoesLol.application;

import com.pedro.lol.campeoesLol.domain.model.Champions;
import com.pedro.lol.campeoesLol.domain.ports.ChampionsRepository;

import java.util.List;

public record ListChampionsUseCase(ChampionsRepository repository) {

    public List<Champions> findAll(){
        return repository.findAll();
    }

}
