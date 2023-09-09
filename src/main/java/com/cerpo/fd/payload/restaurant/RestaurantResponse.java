package com.cerpo.fd.payload.restaurant;

import com.cerpo.fd.model.retailer.category.Category;

import java.util.List;

public record RestaurantResponse(Integer retailerId, String restaurantName, String imgUrl, List<Category> categories) {
}
