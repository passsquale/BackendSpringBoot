package ru.roslyackov.springboot.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.roslyackov.springboot.business.entity.Category;
import ru.roslyackov.springboot.business.ropository.CategoryRepository;

import java.util.List;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll(String email){
        return categoryRepository.findByUserEmailOrderByTitleAsc(email);
    }

    public Category add(Category category){
        return categoryRepository.save(category);
    }
    public Category update(Category category){
        return categoryRepository.save(category);
    }
    public void deleteById(Long id){
        categoryRepository.deleteById(id);
    }
    public List<Category> find(String title, String email){
        return categoryRepository.find(title, email);
    }
    public Category findById(Long id){
        return categoryRepository.findById(id).get();
    }
}
