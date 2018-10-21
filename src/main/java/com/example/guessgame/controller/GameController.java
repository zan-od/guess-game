package com.example.guessgame.controller;

import com.example.guessgame.dto.GameState;
import com.example.guessgame.model.Game;
import com.example.guessgame.model.User;
import com.example.guessgame.service.GameService;
import com.example.guessgame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.List;

@Controller
public class GameController {

    private UserService userService;
    private GameService gameService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Autowired
    public void setGameService(GameService gameService){
        this.gameService = gameService;
    }

    @GetMapping("/")
    public String index(Model model){

        model.addAttribute("activeGames", gameService.getActiveGames());

        return "index";
    }

    @GetMapping("/game/{gameId}")
    public String showGame(Model model, @PathVariable("gameId") Game game){

        model.addAttribute("game", game);

        return "game";
    }

    @GetMapping("/game/create")
    public String createGame(Model model){

        User user = userService.getCurrentUser();
        if (user == null){
            return "redirect:/login";
        }

        Game game = gameService.createGame(user);

        //model.addAttribute("game", game);

        return "redirect:/game/"+game.getId();
    }

    @GetMapping("/game/{gameId}/join")
    public String joinGame(Model model, @PathVariable("gameId") Game game){

        User user = userService.getCurrentUser();
        if (user == null){
            return "redirect:/login";
        }

        gameService.joinGame(game, user);

        //model.addAttribute("game", game);

        return "redirect:/game/"+game.getId();
    }

    @GetMapping("/game/{gameId}/exit")
    public String quitGame(Model model, @PathVariable("gameId") Game game){

        User user = userService.getCurrentUser();
        if (user == null){
            return "redirect:/login";
        }

        gameService.exitGame(game, user);

        return "redirect:/";
    }

    @GetMapping("/game/{gameId}/start")
    public String startGame(Model model, @PathVariable("gameId") Game game){

        User user = userService.getCurrentUser();
        if (user == null){
            return "redirect:/login";
        }

        gameService.startGame(game);

        //model.addAttribute("game", game);

        return "redirect:/game/"+game.getId();
    }

    @GetMapping("/game/{gameId}/getState")
    public @ResponseBody
    GameState getGameState(@PathVariable("gameId") Game game){
        GameState gameState = new GameState();
        gameState.started = game.isStarted();
        gameState.finished = game.isFinished();

        if (game.isStarted()){
            LocalDateTime currentTime = LocalDateTime.now();
            if (game.isFinished()){
                currentTime = game.getFinishTime();
            }

            Duration duration = Duration.between(game.getStartTime(), currentTime);
            gameState.time = formatDuration(duration.getSeconds());
        } else {
            gameState.time = "00:00";
        }

        if (game.getWinner() != null){
            gameState.winnerName = game.getWinner().getUsername();
        }

        return  gameState;
    }

    private String formatDuration(long duration) {
        StringBuilder result = new StringBuilder();
        long seconds = duration;

        if (seconds < 0) {
            seconds = -seconds;
            result.append("-");
        }

        if (seconds < 60){
            result.append("00:").append(String.format("%02d", seconds));
        } else {
            long minutes = (long) seconds/60;
            seconds -= minutes*60;

            long hours = (long) minutes/60;
            minutes -= hours*60;

            if (hours > 0) {
                result.append(hours).append("h ");
            }

            result.append(String.format("%02d", minutes)).append(":").append(String.format("%02d", seconds));
        }

        return result.toString();
    }

    @GetMapping("/game/{gameId}/checkAnswer")
    public @ResponseBody
    GameState checkAnswer(@PathVariable("gameId") Game game, @RequestParam("answer") String answer){

        gameService.checkAnswer(game, userService.getCurrentUser(), answer);

        return  getGameState(game);
    }
}
