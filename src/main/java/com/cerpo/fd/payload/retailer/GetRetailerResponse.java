package com.cerpo.fd.payload.retailer;

import java.math.BigDecimal;

public record GetRetailerResponse(String restaurantName, String companyName, BigDecimal minimumOrder, String description) {
}
