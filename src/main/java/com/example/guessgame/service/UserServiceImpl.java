package com.example.guessgame.service;

import com.example.guessgame.model.User;
import com.example.guessgame.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        if (user.getId() == null){
            //first user is administrator by default
            if (userRepository.count() == 0){
                user.setAdministrator(true);
            }
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    @Override
    public User findUser(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return findUser(auth.getName());
    }

    @Override
    public void updateUserStatistics(User player, boolean isWinner) {
        player.setPlayedGamesCount(getInt(player.getPlayedGamesCount())+1);
        if (isWinner){
            player.setWinsCount(getInt(player.getWinsCount())+1);
        }

        userRepository.save(player);
    }

    private int getInt(Integer value){
        return value == null ? 0 : value.intValue();
    }
}
