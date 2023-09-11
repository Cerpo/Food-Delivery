package com.cerpo.fd.service;

import com.cerpo.fd.exception.FDApiException;
import com.cerpo.fd.model.retailer.Retailer;
import com.cerpo.fd.model.retailer.RetailerRepository;
import com.cerpo.fd.payload.restaurant.Restaurant;
import com.cerpo.fd.payload.restaurant.RestaurantDetails;
import com.cerpo.fd.payload.restaurant.RestaurantResponse;
import com.cerpo.fd.payload.restaurant.RestaurantsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RetailerRepository retailerRepository;

    public RestaurantsResponse getRestaurants() {
        List<Retailer> availableRetails = retailerRepository.findAll();
        return new RestaurantsResponse(convertToResponseEntity(availableRetails));
    }

    private List<Restaurant> convertToResponseEntity(List<Retailer> retailers) {
        List<Restaurant> restaurants = new ArrayList<>();

        for (Retailer retailer : retailers) {
            restaurants.add(new Restaurant(retailer.getRetailerId(),
                                           retailer.getCompanyName(),
                                           retailer.getImgUrl()));
        }

        return restaurants;
    }

    public RestaurantResponse getRestaurant(Integer retailerId) {
        Retailer retailer = retailerRepository.findById(retailerId).orElseThrow(() -> new FDApiException(HttpStatus.BAD_REQUEST, "Restaurant does not exist"));
        return new RestaurantResponse(retailerId,
                                      retailer.getRestaurantName(),
                                      retailer.getImgUrl(),
                                      new RestaurantDetails(retailer.getAddress()),
                                      retailer.getCategories());
    }
}
