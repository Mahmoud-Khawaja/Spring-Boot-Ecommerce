package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    private List<Category> categories = new ArrayList<>();
    private Long nextCategoryId = 1L;

    @Override
    public Category updateCategory(Category category, Long id) {
        Optional<Category> optionalCategory = categories.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            existingCategory.setName(category.getName());
            // Save or persist changes if necessary
            return existingCategory;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found!");
        }
    }

    @Override
    public String deleteCategory(Long id) {
        Category category = categories.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found!"));

        categories.remove(category);
        return "category with id " + id + " deleted!";
    }

    @Override
    public List<Category> getAllCategories(){
        return categories;
    }

    @Override
    public String createCategory(Category category){
        category.setId(nextCategoryId++);
        categories.add(category);
        return "category added!";
    }
}
