package com.cerpo.fd.model.retailer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "retailer_address")
public class RetailerAddress {
    @EqualsAndHashCode.Exclude
    @Id
    @Column(name = "retailer_fk")
    private Integer retailerFk;

    @EqualsAndHashCode.Exclude
    @OneToOne
    @MapsId
    @JoinColumn(name = "retailer_fk")
    private Retailer retailer;

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

    public RetailerAddress(String settlement, Integer zipCode, String streetName, String streetNumber) {
        this.settlement = settlement;
        this.zipCode = zipCode;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
    }
}
