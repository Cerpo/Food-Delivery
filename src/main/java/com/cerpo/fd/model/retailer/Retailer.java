package com.cerpo.fd.model.retailer;

import com.cerpo.fd.model.retailer.menu.Menu;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(name = "company_name")
    private String companyName;

    //Address OneToOne

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_fk", referencedColumnName = "menu_id")
    private Menu menu;
}
