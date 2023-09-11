package com.cerpo.fd.service;

import com.cerpo.fd.exception.FDApiException;
import com.cerpo.fd.model.retailer.Retailer;
import com.cerpo.fd.model.retailer.RetailerAddress;
import com.cerpo.fd.model.retailer.RetailerRepository;
import com.cerpo.fd.model.retailer.category.Category;
import com.cerpo.fd.model.retailer.item.Item;
import com.cerpo.fd.payload.restaurant.RestaurantResponse;
import com.cerpo.fd.payload.restaurant.RestaurantsResponse;
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
        Retailer retailer = createTestRetailer(1);
        given(retailerRepository.findById(1)).willReturn(Optional.of(retailer));

        RestaurantResponse restaurantResponse = restaurantService.getRestaurant(1);

        assertThat(restaurantResponse.restaurantId()).isEqualTo(1);
        assertThat(restaurantResponse.restaurantDetails().restaurantAddress()).isEqualTo(retailer.getAddress());
        assertThat(restaurantResponse.categories().size()).isEqualTo(2);
        assertThat(restaurantResponse.categories().get(0).getItems().size()).isEqualTo(1);
        assertThat(restaurantResponse.categories().get(1).getItems().size()).isEqualTo(2);
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

    @Test
    public void testGetRestaurantBadRequest() {
        given(retailerRepository.findById(2)).willReturn(Optional.empty());

        assertThatThrownBy(() -> restaurantService.getRestaurant(2))
                .isInstanceOf(FDApiException.class)
                .hasMessageContaining("Restaurant does not exist");
    }

    @Test
    public void testGetRestaurants() {
        List<Retailer> retailers = List.of(createTestRetailer(3), createTestRetailer(4));

        given(retailerRepository.findAll()).willReturn(retailers);

        RestaurantsResponse restaurantsResponse = restaurantService.getRestaurants();

        assertThat(restaurantsResponse.restaurants().size()).isEqualTo(2);
        assertThat(restaurantsResponse.restaurants().get(0).restaurantId()).isEqualTo(3);
        assertThat(restaurantsResponse.restaurants().get(1).restaurantId()).isEqualTo(4);
    }
}
