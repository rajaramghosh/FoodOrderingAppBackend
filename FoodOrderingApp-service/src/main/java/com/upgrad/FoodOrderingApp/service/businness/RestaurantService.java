package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantDao restaurantDao;

    /**
     * Get the list of Restaurant by Ratings
     *
     * @return List of Restaurant
     */
    public List<RestaurantEntity> restaurantsByRating() {
        return restaurantDao.restaurantsByRating();
    }
}
