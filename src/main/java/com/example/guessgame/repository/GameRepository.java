package com.example.guessgame.repository;

import com.example.guessgame.model.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Long> {

    List<Game> getGamesByClosed(boolean closed);
}
