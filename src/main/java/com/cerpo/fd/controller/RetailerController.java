package com.cerpo.fd.controller;

import com.cerpo.fd.payload.retailer.*;
import com.cerpo.fd.payload.retailer.address.GetRetailerAddressResponse;
import com.cerpo.fd.payload.retailer.address.UpdateRetailerAddressRequest;
import com.cerpo.fd.payload.retailer.address.UpdateRetailerAddressResponse;
import com.cerpo.fd.service.RetailerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/retailer")
@RequiredArgsConstructor
@PreAuthorize("hasRole('RETAILER')")
public class RetailerController {
    private final RetailerService retailerService;

    @GetMapping
    public ResponseEntity<GetRetailerResponse> getRetailer() {
        return new ResponseEntity<>(retailerService.getRetailer(), HttpStatus.OK);
    }

    @GetMapping("/address")
    public ResponseEntity<GetRetailerAddressResponse> getRetailerAddress() {
        return new ResponseEntity<>(retailerService.getRetailerAddress(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UpdateRetailerResponse> updateRetailer(@Valid @RequestBody UpdateRetailerRequest request) {
        return new ResponseEntity<>(retailerService.updateRetailer(request), HttpStatus.OK);
    }

    @PutMapping("/address")
    public ResponseEntity<UpdateRetailerAddressResponse> updateRetailerAddress(@Valid @RequestBody UpdateRetailerAddressRequest request) {
        return new ResponseEntity<>(retailerService.updateAddress(request), HttpStatus.OK);
    }
}
