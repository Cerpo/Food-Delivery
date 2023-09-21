package com.cerpo.fd.controller;

import com.cerpo.fd.payload.restaurant.GetRestaurantResponse;
import com.cerpo.fd.payload.restaurant.GetRestaurantsResponse;
import com.cerpo.fd.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/restaurant")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CUSTOMER')")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<GetRestaurantsResponse> getRestaurants() {
        return new ResponseEntity<>(restaurantService.getRestaurants(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetRestaurantResponse> getRestaurant(@PathVariable(name = "id") Integer retailerId) {
        return new ResponseEntity<>(restaurantService.getRestaurant(retailerId), HttpStatus.OK);
    }
}
