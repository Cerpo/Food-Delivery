package com.cerpo.fd.model.retailer.menu;

import com.cerpo.fd.model.category.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Integer menuId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_fk", referencedColumnName = "menu_id")
    private List<Category> categories;
}
