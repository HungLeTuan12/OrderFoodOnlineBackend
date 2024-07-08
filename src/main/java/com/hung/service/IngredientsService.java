package com.hung.service;

import com.hung.model.IngredientCategory;

public interface IngredientsService {
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception;

}
