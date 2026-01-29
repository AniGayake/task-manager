package com.task.manager.controller;


import com.task.manager.entity.Task;
import com.task.manager.service.TaskService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public Task create(@RequestBody Task task){
        return taskService.create(task);
    }

    @GetMapping("/{id}")
    public Task get(@PathVariable Long id){
        return taskService.getById(id);
    }

    @GetMapping
    public List<Task> list(){
        return taskService.getAll();
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody Task task){
        return taskService.update(id, task);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        taskService.delete(id);
    }
}

