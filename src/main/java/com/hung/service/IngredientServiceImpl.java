package com.hung.service;

import com.hung.model.IngredientCategory;
import com.hung.model.IngredientsItem;
import com.hung.model.Restaurant;
import com.hung.repository.IngredientCategoryRepository;
import com.hung.repository.IngredientItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientsService {
    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private IngredientItemRepository ingredientItemRepository;

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        IngredientCategory ingredientCategory = new IngredientCategory();
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        ingredientCategory.setRestaurant(restaurant);
        ingredientCategory.setName(name);

        return ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategory(Long id) throws Exception {
        Optional<IngredientCategory> ingredientCategory = ingredientCategoryRepository.findById(id);

        if(ingredientCategory.isEmpty()) {
            throw new Exception("Ingredient category is not found...");
        }

        return ingredientCategory.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingredientCategoryRepository.findByRestaurantId(id);
    }

    @Override
    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) throws Exception {
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category = findIngredientCategory(categoryId);

        IngredientsItem item = new IngredientsItem();
        item.setRestaurant(restaurant);
        item.setName(ingredientName);
        item.setCategory(category);

        IngredientsItem savedItem = ingredientItemRepository.save(item);
        category.getIngredients().add(savedItem);
        return savedItem;
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> ingredientCategory = ingredientItemRepository.findById(id);

        if(ingredientCategory.isEmpty()) {
            throw new Exception("Ingredient category is not found...");
        }

        IngredientsItem items = ingredientCategory.get();
        items.setInStock(!items.isInStock());
        return ingredientItemRepository.save(items);
    }
}
