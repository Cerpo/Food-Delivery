package com.cerpo.fd.payload.retailer.address;

public record GetRetailerAddressResponse(String settlement, Integer zipCode, String streetName, String streetNumber) {
}
