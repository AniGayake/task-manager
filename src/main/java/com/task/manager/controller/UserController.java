package com.task.manager.controller;

import com.task.manager.entity.User;
import com.task.manager.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User create(@RequestBody User user){
        return userService.create(user);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id){
        return userService.getById(id);
    }

    @GetMapping
    public List<User> list(){
        return userService.getAll();
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user){
        return userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

    @GetMapping("/search")
    public List<User> search(@RequestParam String name){
        return userService.search(name);
    }
}

