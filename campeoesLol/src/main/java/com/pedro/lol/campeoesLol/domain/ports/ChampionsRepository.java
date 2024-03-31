package com.pedro.lol.campeoesLol.domain.ports;


import com.pedro.lol.campeoesLol.domain.model.Champions;

import java.util.List;
import java.util.Optional;

public interface ChampionsRepository {
    List<Champions> findAll();


    Optional<Champions> findById(Long id);
}
