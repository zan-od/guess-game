package com.example.guessgame.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name="username")
    private String username;

    @NotNull
    @Column(name="password")
    private String password;

    @NotNull
    @Column(name="played_games")
    private Integer playedGamesCount = 0;

    @NotNull
    @Column(name="wins")
    private Integer winsCount = 0;

    @NotNull
    @Column(name="is_admin")
    private boolean isAdministrator;

    @Transient
    private String confirmPassword;

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

    public boolean isAdministrator() {
        return isAdministrator;
    }

    public void setAdministrator(boolean administrator) {
        isAdministrator = administrator;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
