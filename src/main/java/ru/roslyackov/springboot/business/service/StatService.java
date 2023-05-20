package ru.roslyackov.springboot.business.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.roslyackov.springboot.business.entity.Stat;
import ru.roslyackov.springboot.business.ropository.StatRepository;

@Service
public class StatService {
    private final StatRepository statRepository;
    @Autowired
    public StatService(StatRepository statRepository) {
        this.statRepository = statRepository;
    }
    public Stat findStat(String email){
        return statRepository.findByUserEmail(email);
    }
}
