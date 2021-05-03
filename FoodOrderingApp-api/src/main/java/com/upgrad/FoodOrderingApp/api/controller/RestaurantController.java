package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.RestaurantDetailsResponseAddress;
import com.upgrad.FoodOrderingApp.api.model.RestaurantDetailsResponseAddressState;
import com.upgrad.FoodOrderingApp.api.model.RestaurantList;
import com.upgrad.FoodOrderingApp.api.model.RestaurantListResponse;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * Controller to Handle all Restaurant Related Endpoints
 */

@RestController
@RequestMapping("/")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CategoryService categoryService;

    /**
     * Handles Get All Restaurants
     *
     * @return List of Restaurant
     */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/restaurant", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getAllRestaurants() {

        List<RestaurantEntity> restaurantEntityList = restaurantService.restaurantsByRating();

        RestaurantListResponse restaurantListResponse = new RestaurantListResponse();

        for (RestaurantEntity restaurantEntity : restaurantEntityList) {
            RestaurantDetailsResponseAddressState restaurantDetailsResponseAddressState = new RestaurantDetailsResponseAddressState()
                    .id(UUID.fromString(restaurantEntity.getAddress().getState().getStateUuid()))
                    .stateName(restaurantEntity.getAddress().getState().getStateName());

            RestaurantDetailsResponseAddress restaurantDetailsResponseAddress = new RestaurantDetailsResponseAddress()
                    .id(UUID.fromString(restaurantEntity.getAddress().getUuid()))
                    .flatBuildingName(restaurantEntity.getAddress().getFlatBuilNo())
                    .locality(restaurantEntity.getAddress().getLocality())
                    .city(restaurantEntity.getAddress().getCity())
                    .pincode(restaurantEntity.getAddress().getPincode())
                    .state(restaurantDetailsResponseAddressState);

            String categoriesString = categoryService.getCategoriesByRestaurant(restaurantEntity.getUuid())
                    .stream()
                    .map(o -> String.valueOf(o.getCategoryName()))
                    .collect(Collectors.joining(", "));

            Double rating = BigDecimal.valueOf(restaurantEntity.getCustomerRating()).setScale(1, RoundingMode.HALF_UP).doubleValue();
            RestaurantList restaurantList = new RestaurantList()
                    .id(UUID.fromString(restaurantEntity.getUuid()))
                    .restaurantName(restaurantEntity.getRestaurantName())
                    .photoURL(restaurantEntity.getPhotoUrl())
                    .customerRating(new BigDecimal(rating))
                    .averagePrice(restaurantEntity.getAvgPrice())
                    .numberCustomersRated(restaurantEntity.getNumberCustomersRated())
                    .address(restaurantDetailsResponseAddress)
                    .categories(categoriesString);
            restaurantListResponse.addRestaurantsItem(restaurantList);
        }

        return new ResponseEntity<RestaurantListResponse>(restaurantListResponse, HttpStatus.OK);
    }
}
