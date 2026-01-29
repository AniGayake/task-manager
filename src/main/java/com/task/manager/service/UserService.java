package com.task.manager.service;

import com.task.manager.entity.User;
import com.task.manager.exception.UserNotFoundException;
import com.task.manager.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user){
        LOGGER.info("Creating user");
        return userRepository.save(user);
    }

    public User getById(Long id){
        LOGGER.info("Fetching user by id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("User not found for id: {}", id);
                    return new UserNotFoundException(id);
                });
    }

    public List<User> getAll(){
        LOGGER.info("Fetching all users");
        return userRepository.findAll();
    }

    public User update(Long id, User updated){
        LOGGER.info("Updating user with id: {}", id);
        User u = getById(id);
        u.setFirstName(updated.getFirstName());
        u.setLastName(updated.getLastName());
        u.setDateOfBirth(updated.getDateOfBirth());
        return userRepository.save(u);
    }

    public void delete(Long id){
        LOGGER.info("Deleting user with id: {}", id);
        userRepository.deleteById(id);
    }

    public List<User> search(String name){
        LOGGER.info("Searching users with name: {}", name);
        return userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
    }
}
