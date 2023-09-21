package com.cerpo.fd.payload.restaurant;

import java.util.List;

public record GetRestaurantsResponse(List<Restaurant> restaurants) {
}
