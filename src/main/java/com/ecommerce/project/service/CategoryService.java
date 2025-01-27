package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    public Category updateCategory(Category category, Long id);
    public String deleteCategory(Long id);
    public List<Category> getAllCategories();
    public String createCategory(Category category);
}
