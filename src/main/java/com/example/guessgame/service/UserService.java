package com.example.guessgame.service;

import com.example.guessgame.model.User;

public interface UserService {

    void saveUser(User user);

    User findUser(String username);

}
