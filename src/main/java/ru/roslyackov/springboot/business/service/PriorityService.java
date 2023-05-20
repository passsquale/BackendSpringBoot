package ru.roslyackov.springboot.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.roslyackov.springboot.business.entity.Priority;
import ru.roslyackov.springboot.business.ropository.PriorityRepository;

import java.util.List;

@Service
public class PriorityService {
    private final PriorityRepository priorityRepository;
    @Autowired
    public PriorityService(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }
    public List<Priority> findAll(String email){
        return priorityRepository.findByUserEmailOrderByIdAsc(email);
    }

    public Priority add(Priority priority){
        return priorityRepository.save(priority);
    }
    public Priority update(Priority priority){
        return priorityRepository.save(priority);
    }
    public void deleteById(Long id){
        priorityRepository.deleteById(id);
    }
    public List<Priority> find(String title, String email){
        return priorityRepository.find(title, email);
    }
    public Priority findById(Long id){
        return priorityRepository.findById(id).get();
    }
}
