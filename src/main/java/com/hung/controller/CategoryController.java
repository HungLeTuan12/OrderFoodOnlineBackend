package com.hung.controller;

import com.hung.model.Category;
import com.hung.model.User;
import com.hung.service.CategoryService;
import com.hung.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @PostMapping("/admin/category")
    public ResponseEntity<Category> createNewCategory(@RequestBody Category category,
                                                      @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Category createNewCategory = categoryService.createNewCategory(category.getName(), user.getId());
        return new ResponseEntity<>(createNewCategory, HttpStatus.CREATED);
    }
    @GetMapping("/category/restaurant")
    public ResponseEntity<List<Category>> getCategoryByRestaurantId(
                                                    @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        List<Category> categoryList = categoryService.findCategoryRestaurantId(user.getId());
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<Category> getCategoryById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Category category = categoryService.findCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}
