package com.cerpo.fd.model.retailer.category;

import com.cerpo.fd.model.retailer.item.Item;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @NotBlank
    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_fk", referencedColumnName = "category_id")
    private List<Item> items;

    public Category(String categoryName, List<Item> items) {
        this.categoryName = categoryName;
        this.items = items;
    }
}
