package ru.roslyackov.springboot.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.roslyackov.springboot.business.entity.Stat;
import ru.roslyackov.springboot.business.service.StatService;

@RestController
public class StatController {
    private final StatService statService;
    @Autowired
    public StatController(StatService statService) {
        this.statService = statService;
    }
    @PostMapping("/stat")
    public ResponseEntity<Stat> findByEmail(@RequestBody String email){
        return ResponseEntity.ok(statService.findStat(email));
    }
}
