package ru.roslyackov.springboot.business.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.roslyackov.springboot.business.entity.Priority;
import ru.roslyackov.springboot.business.search.PrioritySearchValues;
import ru.roslyackov.springboot.business.service.PriorityService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/priority")
public class PriorityController {
    private final PriorityService priorityService;

    public PriorityController(PriorityService priorityService) {
        this.priorityService = priorityService;
    }
    @PostMapping("/all")
    public ResponseEntity<List<Priority>> findAll(@RequestBody String email){
        return ResponseEntity.ok(priorityService.findAll(email));
    }
    @PutMapping("/add")
    public ResponseEntity<Priority> add(@RequestBody Priority priority){
        if(priority.getId() != null && priority.getId() != 0){
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(priorityService.add(priority));
    }
    @PatchMapping("/update")
    public ResponseEntity update(@RequestBody Priority priority){

        if(priority.getId() == null || priority.getId() == 0){
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        if(priority.getTitle() == null || priority.getTitle().trim().length() == 0){
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        if(priority.getColor() == null || priority.getColor().trim().length() == 0){
            return new ResponseEntity("missed param: color", HttpStatus.NOT_ACCEPTABLE);
        }

        priorityService.update(priority);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/id")
    public ResponseEntity<Priority> findById(@RequestBody Long id){
        if(id == null || id == 0){
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        Priority priority = null;
        try {
            priority = priorityService.findById(id);
        }catch (NoSuchElementException e){
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(priority);
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Long id){
        try {
            priorityService.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/search")
    public ResponseEntity<List<Priority>> search(@RequestBody PrioritySearchValues prioritySearchValues){
        List<Priority> list = priorityService.find(prioritySearchValues.getTitle(), prioritySearchValues.getEmail());
        return ResponseEntity.ok(list);
    }

}
