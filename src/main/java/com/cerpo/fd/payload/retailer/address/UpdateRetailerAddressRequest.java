package com.cerpo.fd.payload.retailer.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateRetailerAddressRequest {
    @NotBlank
    private String settlement;

    @NotNull
    private Integer zipCode;

    @NotBlank
    private String streetName;

    @NotBlank
    private String streetNumber;
}
