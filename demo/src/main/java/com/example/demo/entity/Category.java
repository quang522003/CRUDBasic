package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    @Column(name = "category_name")
    private String categoryName;

    public Category(){}

    public Category fromCategoryName(String CategoryName){
        Category category = new Category();
        category.setCategoryName(CategoryName);
        return category;
    }

    public Integer getCategoryId(){
        return this.categoryId;
    }

    public void setCategoryId(int id){
        this.categoryId = id;
    }

    public String getCategoryName(){
        return this.categoryName;
    }

    public void setCategoryName(String name){
        this.categoryName = name;
    }


}
