package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category addCategory(String categoryName){
        Category category = new Category();
        category.setCategoryName(categoryName);
        categoryRepository.save(category);
        return category;
    }

    public Optional<Category> getCategoryById(int id){
        return categoryRepository.findById(id) ;
    }

    public Category updateCategory(String categoryName,Integer id){
        Category category = new Category();
        category.setCategoryName(categoryName);
        category.setCategoryId(id);
        categoryRepository.save(category);
        return category;
    }

    public Optional<Category> deleteCategory(int id){
        Optional<Category> categoryDeleted = categoryRepository.findById(id);
        categoryRepository.deleteById(id);
        return categoryDeleted;
    }

    public List<Category> getAll(){
        return categoryRepository.findAll();
    }

}
