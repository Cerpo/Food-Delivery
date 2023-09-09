package com.cerpo.fd.model.retailer.item;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @NotBlank
    @Column(name = "price")
    private BigDecimal price;

    //Allergének
}
