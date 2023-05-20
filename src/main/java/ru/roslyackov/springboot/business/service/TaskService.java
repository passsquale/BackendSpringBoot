package ru.roslyackov.springboot.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.roslyackov.springboot.business.entity.Task;
import ru.roslyackov.springboot.business.ropository.TaskRepository;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;
    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    public List<Task> findAll(String email){
        return taskRepository.findByUserEmailOrderByTitleAsc(email);
    }
    public Task add(Task task){
        return taskRepository.save(task);
    }
    public Task update(Task task){
        return taskRepository.save(task);
    }
    public void deleteById(Long id){
        taskRepository.deleteById(id);
    }
    public Task findById(Long id){
        return taskRepository.findById(id).get();
    }

    public Page<Task> find(String text, Integer completed, Long priorityId, Long categoryId, String email, Date dateFrom, Date dateTo, PageRequest paging) {
        return taskRepository.find(text, completed, priorityId, categoryId, email, dateFrom, dateTo, paging);
    }
}
