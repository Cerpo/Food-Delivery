package com.cerpo.fd.model.retailer;

import com.cerpo.fd.model.retailer.category.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
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

    @ToString.Exclude
    @OneToOne(mappedBy = "retailer", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private RetailerAddress address;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "retailer_fk", referencedColumnName = "retailer_id")
    private List<Category> categories;

    public Retailer(String restaurantName, String companyName, BigDecimal minimumOrder, String description, String imgUrl, RetailerAddress address, List<Category> categories) {
        this.restaurantName = restaurantName;
        this.companyName = companyName;
        this.minimumOrder = minimumOrder;
        this.description = description;
        this.imgUrl = imgUrl;
        this.address = address;
        this.categories = categories;
    }
}
