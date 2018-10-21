package com.example.guessgame.service;

import com.example.guessgame.model.Game;
import com.example.guessgame.model.User;
import com.example.guessgame.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;
    private GameQuestionService gameQuestionService;
    private UserService userService;

    @Autowired
    public void setGameRepository(GameRepository gameRepository){
        this.gameRepository = gameRepository;
    }

    @Autowired
    public void setGameQuestionService(GameQuestionService gameQuestionService){
        this.gameQuestionService = gameQuestionService;
    }

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Override
    public Game createGame(User player) {

        Game game = new Game();
        game.setQuestion(gameQuestionService.getRandomQuestion());

        Set<User> players = new HashSet<>();
        players.add(player);
        game.setPlayers(players);

        gameRepository.save(game);

        return game;
    }

    @Override
    public void joinGame(Game game, User player) {

        if (game.isStarted()){
            return;
        }

        Set<User> players = game.getPlayers();
        if (!players.contains(player)){
            players.add(player);

            gameRepository.save(game);
        }
    }

    @Override
    public void exitGame(Game game, User player) {
        if (game.isStarted()){
            return;
        }
        if (game.isFinished()){
            return;
        }

        Set<User> players = game.getPlayers();
        if (!players.contains(player)){
            return;
        }

        players.remove(player);

        if (players.isEmpty()){
            game.setClosed(true);
        }

        gameRepository.save(game);
    }

    @Override
    public void startGame(Game game) {
        if (game.isStarted()){
            return;
        }

        game.setStartTime(LocalDateTime.now());

        gameRepository.save(game);
    }

    @Override
    public void checkAnswer(Game game, User player, String answer) {
        if (game.isClosed()){
            return;
        }

        if (!game.isStarted()){
            return;
        }
        if (game.isFinished()){
            return;
        }

        if (!gameQuestionService.isCorrectAnswer(game.getQuestion(), answer)){
            return;
        }

        game.setFinishTime(LocalDateTime.now());
        game.setWinner(player);
        game.setClosed(true);

        gameRepository.save(game);

        for (User gamePlayer: game.getPlayers()) {
            userService.updateUserStatistics(gamePlayer, gamePlayer == player);
        }
    }

    @Override
    public List<Game> getActiveGames() {
        return gameRepository.getGamesByClosed(false);
    }
}
