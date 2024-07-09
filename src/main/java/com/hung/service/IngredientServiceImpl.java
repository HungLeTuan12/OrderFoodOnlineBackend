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
    private IngredientItemRepository ingredientItemRepository;
    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;
    @Autowired
    private RestaurantService restaurantService;
    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        IngredientCategory createIngredientCategory = new IngredientCategory();
        createIngredientCategory.setRestaurant(restaurant);
        createIngredientCategory.setName(name);

        return ingredientCategoryRepository.save(createIngredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategory(Long id) throws Exception {
        Optional<IngredientCategory> ingredientCategory = ingredientCategoryRepository.findById(id);

        if(ingredientCategory.isEmpty()) {
            throw new Exception("Ingredient category not found ....");
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
        item.setName(ingredientName);
        item.setRestaurant(restaurant);
        item.setCategory(category);

        IngredientsItem savedIngredientItem = ingredientItemRepository.save(item);
        category.getIngredients().add(savedIngredientItem);

        return savedIngredientItem;
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> item = ingredientItemRepository.findById(id);

        if(item.isEmpty()) {
            throw new Exception("Item not found ....");
        }

        IngredientsItem items = item.get();
        items.setInStock(!items.isInStock());

        return ingredientItemRepository.save(items);
    }
}
