package com.task.manager.service;

import com.task.manager.entity.User;
import com.task.manager.exception.UserNotFoundException;
import com.task.manager.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User create(User user){
        return userRepository.save(user);
    }

    public User getById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User update(Long id, User updated){
        User u = getById(id);
        u.setFirstName(updated.getFirstName());
        u.setLastName(updated.getLastName());
        u.setDateOfBirth(updated.getDateOfBirth());
        return userRepository.save(u);
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

    public List<User> search(String name){
        return userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
    }
}
