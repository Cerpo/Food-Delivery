package com.cerpo.fd.service;

import com.cerpo.fd.exception.FDApiException;
import com.cerpo.fd.model.retailer.Retailer;
import com.cerpo.fd.model.retailer.RetailerAddress;
import com.cerpo.fd.model.retailer.RetailerRepository;
import com.cerpo.fd.payload.retailer.*;
import com.cerpo.fd.payload.retailer.address.GetRetailerAddressResponse;
import com.cerpo.fd.payload.retailer.address.UpdateRetailerAddressRequest;
import com.cerpo.fd.payload.retailer.address.UpdateRetailerAddressResponse;
import com.cerpo.fd.util.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetailerService {
    private final RetailerRepository retailerRepository;

    public GetRetailerResponse getRetailer() {
        Retailer retailer = getRetailer(getRetailerId());

        return new GetRetailerResponse(retailer.getRestaurantName(), retailer.getCompanyName(), retailer.getMinimumOrder(), retailer.getDescription());
    }

    public GetRetailerAddressResponse getRetailerAddress() {
        Retailer retailer = getRetailer(getRetailerId());
        RetailerAddress retailerAddress = retailer.getAddress();
        if (retailerAddress != null) {
            return new GetRetailerAddressResponse(retailerAddress.getSettlement(),
                                                  retailerAddress.getZipCode(),
                                                  retailerAddress.getStreetName(),
                                                  retailerAddress.getStreetNumber());
        }
        return new GetRetailerAddressResponse("", 0, "", "");
    }

    public UpdateRetailerResponse updateRetailer(UpdateRetailerRequest request) {
        Retailer retailer = getRetailer(getRetailerId());

        if (request.getRestaurantName() != null) {
            retailer.setRestaurantName(request.getRestaurantName());
        }
        if (request.getMinimumOrder() != null) {
            retailer.setMinimumOrder(request.getMinimumOrder());
        }
        if (request.getDescription() != null) {
            retailer.setDescription(request.getDescription());
        }
        retailerRepository.save(retailer);

        return new UpdateRetailerResponse();
    }

    public UpdateRetailerAddressResponse updateAddress(UpdateRetailerAddressRequest request) {
        Retailer retailer = getRetailer(getRetailerId());
        RetailerAddress retailerAddress;
        if (retailer.getAddress() != null) {
            retailerAddress = retailer.getAddress();
            retailerAddress.setSettlement(request.getSettlement());
            retailerAddress.setZipCode(request.getZipCode());
            retailerAddress.setStreetName(request.getStreetName());
            retailerAddress.setStreetNumber(request.getStreetNumber());
        } else {
            retailerAddress = new RetailerAddress(request.getSettlement(), request.getZipCode(), request.getStreetName(), request.getStreetNumber());
            retailer.setAddress(retailerAddress);
            retailer.getAddress().setRetailer(retailer);
        }
        retailerRepository.save(retailer);

        return new UpdateRetailerAddressResponse();
    }

    private Retailer getRetailer(Integer retailerId) {
        return retailerRepository.findById(retailerId).orElseThrow(() -> new FDApiException(HttpStatus.BAD_REQUEST, "Retailer does not exist"));
    }

    private Integer getRetailerId() {
        return AppUtils.getCurrentlyAuthenticatedUser().getId();
    }
}
