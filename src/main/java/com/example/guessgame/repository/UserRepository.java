package com.example.guessgame.repository;

import com.example.guessgame.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByUsername(String username);
}
