package com.cerpo.fd.repository;

import com.cerpo.fd.model.retailer.Retailer;
import com.cerpo.fd.model.retailer.RetailerAddress;
import com.cerpo.fd.model.retailer.RetailerRepository;
import com.cerpo.fd.model.retailer.category.Category;
import com.cerpo.fd.model.retailer.item.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class TestRetailerRepository {
    @Autowired
    private RetailerRepository retailerRepository;

    @Test
    public void testFindRetailerById() {
        Retailer retailer = createTestRetailer(1);

        retailerRepository.save(retailer);
        Optional<Retailer> dbRetailer = retailerRepository.findById(1);

        assertThat(dbRetailer).isNotNull().get().isEqualTo(retailer);
    }

    private Retailer createTestRetailer(Integer retailerId) {
        RetailerAddress retailerAddress = new RetailerAddress(1, "Budapest", 1111, "Fehérvári út", "10");
        List<Category> categories = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        List<Item> items2 = new ArrayList<>();

        Category category, category2;
        Item item, item2, item3;

        item = new Item(1, "Cola", "Sample desc", new BigDecimal("2000"));
        item2 = new Item(2, "Beef", "Sample desc", new BigDecimal("5000"));
        item3 = new Item(3, "Chicken", "Sample desc", new BigDecimal("4000"));
        items.add(item);
        items2.add(item2);
        items2.add(item3);

        category = new Category(1, "Drink", items);
        category2 = new Category(2, "Meat", items2);
        categories.add(category);
        categories.add(category2);

        return new Retailer(retailerId, "McDaniels",
                "Daniels&Daniels", new BigDecimal("2000"),
                "Sample Desc", "src/test",
                retailerAddress, categories);
    }
}
