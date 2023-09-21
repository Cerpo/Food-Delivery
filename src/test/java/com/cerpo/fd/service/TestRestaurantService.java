package com.cerpo.fd.service;

import com.cerpo.fd.exception.FDApiException;
import com.cerpo.fd.model.retailer.Retailer;
import com.cerpo.fd.model.retailer.RetailerAddress;
import com.cerpo.fd.model.retailer.RetailerRepository;
import com.cerpo.fd.model.retailer.category.Category;
import com.cerpo.fd.model.retailer.item.Item;
import com.cerpo.fd.payload.restaurant.GetRestaurantResponse;
import com.cerpo.fd.payload.restaurant.GetRestaurantsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class TestRestaurantService {
    @Mock
    private RetailerRepository retailerRepository;
    private RestaurantService restaurantService;

    @BeforeEach
    void setUp() {
        restaurantService = new RestaurantService(retailerRepository);
    }

    @Test
    public void testGetRestaurant() {
        Retailer retailer = createTestRetailer();
        given(retailerRepository.findById(1)).willReturn(Optional.of(retailer));

        GetRestaurantResponse restaurantResponse = restaurantService.getRestaurant(1);

        assertThat(restaurantResponse.restaurantId()).isEqualTo(1);
        assertThat(restaurantResponse.restaurantDetails().restaurantAddress()).isEqualTo(retailer.getAddress());
        assertThat(restaurantResponse.categories()).isEqualTo(retailer.getCategories());
    }

    private Retailer createTestRetailer() {
        Retailer retailer;
        RetailerAddress retailerAddress = new RetailerAddress("Budapest", 1111, "Fehérvári út", "10");
        List<Category> categories = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        List<Item> items2 = new ArrayList<>();

        Category category, category2;
        Item item, item2, item3;

        item = new Item("Cola", "Sample desc", new BigDecimal("2000"));
        item2 = new Item("Beef", "Sample desc", new BigDecimal("5000"));
        item3 = new Item("Chicken", "Sample desc", new BigDecimal("4000"));
        items.add(item);
        items2.add(item2);
        items2.add(item3);

        category = new Category("Drink", items);
        category2 = new Category("Meat", items2);
        categories.add(category);
        categories.add(category2);

        retailer = new Retailer("McDaniels",
                    "Daniels&Daniels", new BigDecimal("2000"),
                    "Sample Desc", "src/test",
                    retailerAddress, categories);
        retailer.getAddress().setRetailer(retailer);
        return retailer;
    }

    @Test
    public void testGetRestaurantBadRequest() {
        given(retailerRepository.findById(2)).willReturn(Optional.empty());

        assertThatThrownBy(() -> restaurantService.getRestaurant(2))
                .isInstanceOf(FDApiException.class)
                .hasMessageContaining("Restaurant does not exist");
    }

    @Test
    public void testGetRestaurants() {
        List<Retailer> retailers = List.of(createTestRetailer(), createTestRetailer());

        given(retailerRepository.findAll()).willReturn(retailers);

        GetRestaurantsResponse restaurantsResponse = restaurantService.getRestaurants();

        assertThat(restaurantsResponse.restaurants().size()).isEqualTo(2);
        assertThat(restaurantsResponse.restaurants().get(0).restaurantName()).isNotNull();
        assertThat(restaurantsResponse.restaurants().get(1).restaurantName()).isNotNull();
    }
}
