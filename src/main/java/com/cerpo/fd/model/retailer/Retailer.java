package com.cerpo.fd.model.retailer;

import com.cerpo.fd.model.retailer.category.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "retailer")
public class Retailer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "retailer_id")
    private Integer retailerId;

    @NotBlank
    @Column(name = "restaurant_name")
    private String restaurantName;

    @NotBlank
    @Column(name = "company_name")
    private String companyName;

    @Digits(integer = 5, fraction = 0)
    @Column(name = "minimum_order")
    private BigDecimal minimumOrder;

    @Column(name = "description")
    private String description;

    @Column(name = "img_url")
    private String imgUrl;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_fk", referencedColumnName = "address_id")
    private RetailerAddress address;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "retailer_fk", referencedColumnName = "retailer_id")
    private List<Category> categories;
}
