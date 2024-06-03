package com.example.demo.controller;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CategoryRequestDTO;
import com.example.demo.entity.Category;
import com.example.demo.exception.ResourceNotFound;
import com.example.demo.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class Controller {
    @Autowired
    private CategoryService categoryService;
    private Logger logger = LogManager.getLogger(Controller.class);

    @CrossOrigin
    @GetMapping("/categories")
    public ResponseEntity<Object> getAll() {
        try{
            logger.info("Client goi API getall()");
            return new ResponseEntity<>(categoryService.getAll(),HttpStatus.valueOf(200));
        }
        catch(DataAccessException e){ //lỗi truy cập 
            logger.error("Loi truy cap");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @CrossOrigin
    @GetMapping("/categories/{id}.json")
    public ResponseEntity<Object> getCategoryById(@PathVariable Integer id) {
        try{
            logger.info("Client xem category "+id);
            return new ResponseEntity<>(categoryService.getCategoryById(id),HttpStatus.valueOf(200));
        }
        catch(DataAccessException e){ //lỗi truy cập 
            logger.error("Loi truy cap");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } 
        
    }

    @CrossOrigin
    @PostMapping("/categories.json") // Thêm chỉ với trường categoryName
    public ResponseEntity<Object> addCategory(@RequestBody CategoryRequestDTO categoryRequestDTO) {
        try{
            Category categoryResponse = categoryService.addCategory(categoryRequestDTO.getCategoryName());
            logger.info("Client them category " + categoryResponse.getCategoryId());
            return new ResponseEntity<>(categoryResponse,HttpStatus.valueOf(201));       
        }catch(DataAccessException e){ //lỗi truy cập 
            logger.error("Loi truy cap");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } 
        
    }

    @CrossOrigin
    @PutMapping("/categories/{id}.json")
    public ResponseEntity<Object> updateCategory(@RequestBody CategoryRequestDTO categoryRequestDTO,@PathVariable Integer id) { 
        try{
            Optional<Category> a = categoryService.getCategoryById(id);
            if(a.isEmpty()){
                logger.error("Client sua that bai");
                throw new ResourceNotFound("Sửa thất bại (không có bản ghi với id = " + id + ")");
            }
            else{
                Category CategoryResponse = categoryService.updateCategory(categoryRequestDTO.getCategoryName(),id);
                logger.info("Client sua category " + id);
                return new ResponseEntity<>(CategoryResponse,HttpStatus.valueOf(200));
            }
        }catch(DataAccessException e){ //lỗi truy cập 
            logger.error("Loi truy cap");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }catch (ResourceNotFound e){ // lỗi không thấy bản ghi 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }     
    }

    @CrossOrigin
    @DeleteMapping("/categories/{id}.json")
    public ResponseEntity<Object> deleteCategory(@PathVariable Integer id) {
        try{
            Optional<Category> categoryDeleted = categoryService.deleteCategory(id);
            logger.warn("Client xoa category " + id);
            return new ResponseEntity<>(categoryDeleted,HttpStatus.valueOf(200));
        }catch(DataAccessException e){ //lỗi truy cập 
            logger.error("Loi truy cap");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
    } 
}
