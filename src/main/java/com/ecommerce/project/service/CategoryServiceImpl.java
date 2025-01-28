package com.ecommerce.project.service;

import com.ecommerce.project.exception.APIException;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryDTO updateCategory(CategoryDTO category, Long id) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setName(category.getName());
                    return modelMapper.map(categoryRepository.save(existingCategory),CategoryDTO.class);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",id));
    }

    @Override
    public CategoryDTO deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category","categoryId",id);
        }
        CategoryDTO categoryDTO = modelMapper.map(categoryRepository.findById(id),CategoryDTO.class);
        categoryRepository.deleteById(id);
        return categoryDTO;
    }

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber,Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Category>categoryPage = categoryRepository.findAll(pageable);
        List<Category>categories = categoryPage.getContent();
        if(categories.isEmpty()){
            throw new APIException("NO categories till now ");
        }
        List<CategoryDTO>categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category,CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(pageable.getPageNumber());
        categoryResponse.setPageSize(pageable.getPageSize());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        return categoryResponse;
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
