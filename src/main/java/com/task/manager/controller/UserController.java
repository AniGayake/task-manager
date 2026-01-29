package com.task.manager.controller;

import com.task.manager.entity.User;
import com.task.manager.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User create(@RequestBody User user){
        LOGGER.info("Create user request received");
        return userService.create(user);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id){
        LOGGER.info("Get user request received for id: {}", id);
        return userService.getById(id);
    }

    @GetMapping
    public List<User> list(){
        LOGGER.info("List all users request received");
        return userService.getAll();
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user){
        LOGGER.info("Update user request received for id: {}", id);
        return userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        LOGGER.info("Delete user request received for id: {}", id);
        userService.delete(id);
    }

    @GetMapping("/search")
    public List<User> search(@RequestParam String name){
        LOGGER.info("Search user request received for name: {}", name);
        return userService.search(name);
    }
}
