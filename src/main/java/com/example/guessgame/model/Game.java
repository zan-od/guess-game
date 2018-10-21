package com.example.guessgame.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToMany
    @JoinTable(name = "game_players",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    private Set<User> players;

    @OneToOne
    @JoinColumn(name = "winner_id")
    private User winner;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "finish_time")
    private LocalDateTime finishTime;

    @NotNull
    @OneToOne
    @JoinColumn(name = "question_id")
    private GameQuestion question;

    @Column(name = "closed", nullable = false)
    private boolean closed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<User> getPlayers() {
        return players;
    }

    public void setPlayers(Set<User> players) {
        this.players = players;
    }

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public GameQuestion getQuestion() {
        return question;
    }

    public void setQuestion(GameQuestion question) {
        this.question = question;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean isStarted(){
        return startTime != null;
    }

    public boolean isFinished(){
        return finishTime != null;
    }

}
