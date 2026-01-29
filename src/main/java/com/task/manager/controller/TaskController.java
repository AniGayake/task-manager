package com.task.manager.controller;

import com.task.manager.entity.Task;
import com.task.manager.service.TaskService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public Task create(@RequestBody Task task){
        LOGGER.info("Create task request received");
        return taskService.create(task);
    }

    @GetMapping("/{id}")
    public Task get(@PathVariable Long id){
        LOGGER.info("Get task request received for id: {}", id);
        return taskService.getById(id);
    }

    @GetMapping
    public List<Task> list(){
        LOGGER.info("List all tasks request received");
        return taskService.getAll();
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody Task task){
        LOGGER.info("Update task request received for id: {}", id);
        return taskService.update(id, task);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        LOGGER.info("Delete task request received for id: {}", id);
        taskService.delete(id);
    }
}
