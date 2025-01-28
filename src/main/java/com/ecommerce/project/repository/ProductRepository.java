package com.ecommerce.project.repository;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByCategoryOrderByPriceAsc(Category category);
}
