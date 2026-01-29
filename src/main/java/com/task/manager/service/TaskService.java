package com.task.manager.service;

import com.task.manager.entity.Task;
import com.task.manager.exception.TaskNotFoundException;
import com.task.manager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task create(Task task){
        return taskRepository.save(task);
    }

    public Task getById(Long id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public List<Task> getAll(){
        return taskRepository.findAll();
    }

    public Task update(Long id, Task updated){
        Task t = getById(id);
        t.setTitle(updated.getTitle());
        t.setDescription(updated.getDescription());
        t.setDueDate(updated.getDueDate());
        t.setStatus(updated.getStatus());
        return taskRepository.save(t);
    }

    public void delete(Long id){
        taskRepository.deleteById(id);
    }
}
