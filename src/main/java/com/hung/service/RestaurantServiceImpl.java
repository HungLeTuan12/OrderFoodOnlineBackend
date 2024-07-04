package com.hung.service;

import com.hung.dto.RestaurantDto;
import com.hung.model.Address;
import com.hung.model.Restaurant;
import com.hung.model.User;
import com.hung.repository.AddressRepository;
import com.hung.repository.RestaurantRepository;
import com.hung.repository.UserRepository;
import com.hung.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address = addressRepository.save(req.getAddress());

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(req.getAddress());
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRequest) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        if(restaurant.getCuisineType() != null) {
            restaurant.setCuisineType(updatedRequest.getCuisineType());
        }
        if(restaurant.getDescription() != null) {
            restaurant.setDescription(updatedRequest.getDescription());
        }
        if(restaurant.getName() != null) {
            restaurant.setName(updatedRequest.getName());
        }

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        if(restaurant != null) {
            restaurantRepository.delete(restaurant);
        }
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception
    {
        Optional<Restaurant> opt = restaurantRepository.findById(id);

        if(opt.isEmpty()) {
            throw new Exception("Restaurant not found with id: " + id);
        }
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);

        if(restaurant == null) {
            throw new Exception("Restaurant not found with owner id !");
        }

        return restaurant;
    }

    @Override
    public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setDescription(restaurant.getDescription());
        restaurantDto.setImages(restaurant.getImages());
        restaurantDto.setTitle(restaurant.getName());
        restaurantDto.setId(restaurantId);

        if(user.getFavourites().contains(restaurantDto)) {
            user.getFavourites().remove(restaurantDto);
        }
        else
            user.getFavourites().add(restaurantDto);
        userRepository.save(user);
        return restaurantDto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}
