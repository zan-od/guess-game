package com.example.guessgame.repository;

import com.example.guessgame.model.GameQuestion;
import org.springframework.data.repository.CrudRepository;

public interface GameQuestionRepository extends CrudRepository<GameQuestion, Long> {
}
