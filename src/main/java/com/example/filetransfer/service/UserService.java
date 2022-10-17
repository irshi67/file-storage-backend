package com.example.filetransfer.service;

import com.example.filetransfer.model.User;
import com.example.filetransfer.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void registerNewUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
            user.setId(UUID.randomUUID().toString());
            user.setLoggedIn(false);
            userRepository.save(user);
        } else {
            throw new IllegalStateException("User with email " + user.getEmail() + "is taken");
        }
    }

    public User loginUser(User user) {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            User userToSave = userOptional.get();
            if (userToSave.getPassword().equals(user.getPassword())) {
                userToSave.setLoggedIn(true);
                userRepository.save(userToSave);
            }
        }
        return userOptional.get();
    }

    public void logoutUser(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User userToSave = userOptional.get();
            userToSave.setLoggedIn(false);
            userRepository.save(userToSave);
        }
    }
}
