package com.hung.service;


import com.hung.model.Category;

import java.util.List;

public interface CategoryService {
    public Category createNewCategory(String name, Long userId) throws Exception;
    public List<Category> findCategoryRestaurantId(Long id) throws Exception;
    public Category findCategoryById(Long id) throws Exception;

}
