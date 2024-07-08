package com.hung.controller;

import com.hung.model.Food;
import com.hung.model.Restaurant;
import com.hung.model.User;
import com.hung.request.CreateFoodRequest;
import com.hung.response.MessageResponse;
import com.hung.service.FoodService;
import com.hung.service.RestaurantService;
import com.hung.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {
    @Autowired
    private FoodService foodService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;
    @PostMapping
    public ResponseEntity<Food> createNewFood(@RequestBody CreateFoodRequest req,
                                              @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurantId());
        Food food = foodService.createFood(req, req.getCategory(), restaurant);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }
    @PostMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@RequestHeader("Authorization") String jwt,
                                                      @PathVariable Long id) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        foodService.deleteFood(id);

        MessageResponse response = new MessageResponse();
        response.setMessage("Food deleted successfully ....");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvailabilityStatus(@RequestHeader("Authorization") String jwt,
                                                      @PathVariable Long id) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Food food = foodService.updateAvailabilityStatus(id);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}
