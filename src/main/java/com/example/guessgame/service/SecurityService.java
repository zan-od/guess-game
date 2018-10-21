package com.example.guessgame.service;

public interface SecurityService {
    String getLoggedInUsername();

    void autoLogin(String username, String password);
}
