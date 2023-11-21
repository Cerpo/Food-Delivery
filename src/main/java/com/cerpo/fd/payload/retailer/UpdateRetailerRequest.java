package com.cerpo.fd.payload.retailer;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class UpdateRetailerRequest {
    private String restaurantName;
    private BigDecimal minimumOrder;
    private String description;
}
