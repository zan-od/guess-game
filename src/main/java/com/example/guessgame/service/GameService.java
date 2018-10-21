package com.example.guessgame.service;

import com.example.guessgame.model.Game;
import com.example.guessgame.model.User;

import java.util.List;

public interface GameService {

    Game createGame(User player);

    void joinGame(Game game, User player);

    void exitGame(Game game, User player);

    void startGame(Game game);

    void checkAnswer(Game game, User player, String answer);

    List<Game> getActiveGames();
}
