package com.example.guessgame.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="played_games")
    private Integer playedGamesCount;

    @Column(name="wins")
    private Integer winsCount;

    @ManyToMany
    @JoinTable(name = "game_players",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
    private List<Game> games;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPlayedGamesCount() {
        return playedGamesCount;
    }

    public void setPlayedGamesCount(Integer playedGamesCount) {
        this.playedGamesCount = playedGamesCount;
    }

    public Integer getWinsCount() {
        return winsCount;
    }

    public void setWinsCount(Integer winsCount) {
        this.winsCount = winsCount;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}