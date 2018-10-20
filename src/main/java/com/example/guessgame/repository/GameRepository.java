package com.example.guessgame.repository;

import com.example.guessgame.model.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Long> {
}
