package com.example.guessgame.service;

import com.example.guessgame.model.GameQuestion;

import java.util.List;

public interface GameQuestionService {

    void saveQuestion(GameQuestion gameQuestion);

    List<GameQuestion> listQuestions();

    GameQuestion getQuestion(Long id);

    GameQuestion getRandomQuestion();

    boolean isCorrectAnswer(GameQuestion question, String answer);
}
