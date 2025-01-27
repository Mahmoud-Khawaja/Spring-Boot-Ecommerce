package com.ecommerce.project.service;

import com.ecommerce.project.exception.APIException;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category updateCategory(Category category, Long id) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setName(category.getName());
                    return categoryRepository.save(existingCategory);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",id));
    }

    @Override
    public String deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category","categoryId",id);
        }
        categoryRepository.deleteById(id);
        return "Category with id " + id + " deleted!";
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category>categories = categoryRepository.findAll();
        if(categories.isEmpty()){
            throw new APIException("NO categories till now ");
        }
        return categories;
    }

    @Override
    public String createCategory(Category category) {
        Category savedCategory = categoryRepository.findByName(category.getName());
        if(savedCategory!=null){
            throw new APIException("this category: "+ savedCategory.getName()+ " already exist!");
        }
        categoryRepository.save(category);
        return "Category added!";
    }
}
