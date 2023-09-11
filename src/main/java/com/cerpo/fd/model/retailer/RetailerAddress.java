package com.cerpo.fd.model.retailer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "retailer_address")
public class RetailerAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;

    @NotBlank
    @Column(name = "settlement")
    private String settlement;

    @NotNull
    @Column(name = "zip_code")
    private Integer zipCode;

    @NotBlank
    @Column(name = "street_name")
    private String streetName;

    @NotBlank
    @Column(name = "street_number")
    private String streetNumber;
}
