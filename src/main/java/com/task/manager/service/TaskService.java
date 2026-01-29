package com.task.manager.service;

import com.task.manager.entity.Task;
import com.task.manager.exception.TaskNotFoundException;
import com.task.manager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    private TaskRepository taskRepository;

    public TaskService(final TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public Task create(Task task){
        LOGGER.info("Creating task");
        return taskRepository.save(task);
    }

    public Task getById(Long id){
        LOGGER.info("Fetching task by id: {}", id);
        return taskRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Task not found for id: {}", id);
                    return new TaskNotFoundException(id);
                });
    }

    public List<Task> getAll(){
        LOGGER.info("Fetching all tasks");
        return taskRepository.findAll();
    }

    public Task update(Long id, Task updated){
        LOGGER.info("Updating task with id: {}", id);
        Task t = getById(id);
        t.setTitle(updated.getTitle());
        t.setDescription(updated.getDescription());
        t.setDueDate(updated.getDueDate());
        t.setStatus(updated.getStatus());
        return taskRepository.save(t);
    }

    public void delete(Long id){
        LOGGER.info("Deleting task with id: {}", id);

        if (!taskRepository.existsById(id)) {
            LOGGER.error("Task not found for delete with id: {}", id);
            throw new TaskNotFoundException(id);
        }

        taskRepository.deleteById(id);
        LOGGER.info("Task deleted successfully with id: {}", id);
    }

}
