package com.cerpo.fd.model.retailer.item;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer itemId;

    @NotBlank
    @Column(name = "item_name")
    private String itemName;

    @NotBlank
    @Column(name = "description")
    private String description;

    @Digits(integer = 5, fraction = 0)
    @Column(name = "price")
    private BigDecimal price;

    //Allergének

    public Item(String itemName, String description, BigDecimal price) {
        this.itemName = itemName;
        this.description = description;
        this.price = price;
    }
}
