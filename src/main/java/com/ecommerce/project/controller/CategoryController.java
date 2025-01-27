package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/api/public/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category>categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/api/public/categories")
    public ResponseEntity<String> createCategory(@RequestBody Category category){
        categoryService.createCategory(category);
        return new ResponseEntity<>("created a new category", HttpStatus.CREATED);
    }
    @DeleteMapping("/api/admin/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        try {
            String status = categoryService.deleteCategory(id);
            return new ResponseEntity<>(status, HttpStatus.OK);
        }catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }
    }
    @PutMapping("/api/admin/categories/{id}")
    public ResponseEntity<String> updateCategory(@RequestBody Category category,
                                                 @PathVariable Long id){
        try{
            Category savedCategory = categoryService.updateCategory(category,id);
            return new ResponseEntity<>("category with "+ savedCategory.getId() + " saved", HttpStatus.OK);
        }catch(ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(),HttpStatus.NOT_FOUND);
        }
    }
}
