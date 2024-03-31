package com.pedro.lol.campeoesLol.aplication;

import com.pedro.lol.campeoesLol.application.ListChampionsUseCase;
import com.pedro.lol.campeoesLol.domain.model.Champions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ListChampionsUseCaseIntegrationTeste {

    @Autowired
    private ListChampionsUseCase listChampionsUseCase;

    @Test
    public void testeListChampions(){
        List<Champions> champions =  listChampionsUseCase.findAll();

        Assertions.assertEquals(12, champions.size());
    }
}