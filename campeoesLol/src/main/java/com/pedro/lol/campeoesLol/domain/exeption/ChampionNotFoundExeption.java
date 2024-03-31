package com.pedro.lol.campeoesLol.domain.exeption;

public class ChampionNotFoundExeption extends RuntimeException {
    public ChampionNotFoundExeption(Long championId) {
        super("Campeão %d não existe." .formatted(championId));
    }
}
