package com.example.TravelJournal.TravelJournal.Service;

import com.example.TravelJournal.TravelJournal.Model.Users;
import com.example.TravelJournal.TravelJournal.Repository.UserRepository;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UserService {
    //Changed due to error 1
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    //Trying another approach by creating AppConfig Class
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }




    public Users saveUser(@NonNull Users user) {
        // Check if username already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Users findByUsername(String username) {
        logger.info("Searching for user with username: {}", username);

        return userRepository.findByUsername(username);
    }

    public Users findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<Users> findAllUsers() {
        return userRepository.findAll();
    }

    public Users updateUser(String id, String newUsername, String newPassword) {
        Users user = findById(id);
        if (user != null) {
            user.setUsername(newUsername);
            if (newPassword != null && !newPassword.isEmpty()) {
                user.setPassword(passwordEncoder.encode(newPassword));
            }
            return userRepository.save(user);
        }
        return null;
    }

    public boolean deleteUserById(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean deleteUserByUsername(String username) {
        Users user = findByUsername(username);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }
}