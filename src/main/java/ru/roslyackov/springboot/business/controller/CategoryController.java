package ru.roslyackov.springboot.business.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.roslyackov.springboot.business.entity.Category;
import ru.roslyackov.springboot.business.search.CategorySearchValues;
import ru.roslyackov.springboot.business.service.CategoryService;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/all")
    public ResponseEntity<List<Category>> findAll(@RequestBody String email){
        return ResponseEntity.ok(categoryService.findAll(email));
    }

    @PutMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category category){
        if(category.getId() != null && category.getId() != 0){
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if(category.getTitle() == null || category.getTitle().trim().length() == 0){
            return new ResponseEntity("missed param: title MUST be not null", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(categoryService.add(category));
    }
    @PatchMapping("/update")
    public ResponseEntity update(@RequestBody Category category){

        if(category.getId() == null || category.getId() == 0){
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        if(category.getTitle() == null || category.getTitle().trim().length() == 0){
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        categoryService.update(category);
        return new ResponseEntity(HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Long id){
        try {
            categoryService.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/search")
    public ResponseEntity<List<Category>> search(@RequestBody CategorySearchValues categorySearchValues){
        List<Category> list = categoryService.find(categorySearchValues.getTitle(), categorySearchValues.getEmail());
        return ResponseEntity.ok(list);
    }
    @PostMapping("/id")
    public ResponseEntity<Category> findById(@RequestBody Long id){
        Category category = null;
        try {
            category = categoryService.findById(id);
        }catch (NoSuchElementException e){
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(category);
    }
}
