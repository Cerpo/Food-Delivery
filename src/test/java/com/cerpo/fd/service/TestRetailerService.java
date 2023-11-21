package com.cerpo.fd.service;

import com.cerpo.fd.exception.FDApiException;
import com.cerpo.fd.model.retailer.Retailer;
import com.cerpo.fd.model.retailer.RetailerAddress;
import com.cerpo.fd.model.retailer.RetailerRepository;
import com.cerpo.fd.model.retailer.category.Category;
import com.cerpo.fd.model.retailer.item.Item;
import com.cerpo.fd.model.user.Role;
import com.cerpo.fd.model.user.User;
import com.cerpo.fd.payload.retailer.GetRetailerResponse;
import com.cerpo.fd.payload.retailer.UpdateRetailerRequest;
import com.cerpo.fd.payload.retailer.UpdateRetailerResponse;
import com.cerpo.fd.payload.retailer.address.GetRetailerAddressResponse;
import com.cerpo.fd.payload.retailer.address.UpdateRetailerAddressRequest;
import com.cerpo.fd.payload.retailer.address.UpdateRetailerAddressResponse;
import com.cerpo.fd.util.AppUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TestRetailerService {
    @Mock
    private RetailerRepository retailerRepository;

    private RetailerService retailerService;

    static MockedStatic<AppUtils> dummy = mockStatic(AppUtils.class);

    @BeforeEach
    void setUp() {
        retailerService = new RetailerService(retailerRepository);
    }

    private Retailer createTestRetailer(Boolean addRetailerAddress) {
        Retailer retailer;
        RetailerAddress retailerAddress;

        if (addRetailerAddress) {
            retailerAddress = getRetailerAddress();
        } else {
            retailerAddress = null;
        }
        List<Category> categories = getCategories();

        retailer = new Retailer("McDaniels",
                "Daniels&Daniels", new BigDecimal("2000"),
                "Sample Desc", "src/test",
                retailerAddress, categories);
        if (addRetailerAddress) {
            retailer.getAddress().setRetailer(retailer);
        }
        return retailer;
    }

    private RetailerAddress getRetailerAddress() {
        return new RetailerAddress("Budapest", 1111, "Fehérvári út", "10");
    }

    private static List<Category> getCategories() {
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
        return categories;
    }

    @Test
    public void testGetRetailer() {
        Retailer retailer = createTestRetailer(true);
        dummy.when(AppUtils::getCurrentlyAuthenticatedUser).thenReturn(new User(1,
                                                                          "Test@test.com",
                                                                                new BCryptPasswordEncoder().encode("TestPW"),
                                                                                new Date(),
                                                                                new Date(),
                                                                                Role.ROLE_CUSTOMER));
        given(retailerRepository.findById(1)).willReturn(Optional.of(retailer));

        GetRetailerResponse resp = retailerService.getRetailer();
        assertThat(resp).isNotNull();
        assertThat(resp.restaurantName()).isEqualTo(retailer.getRestaurantName());
        assertThat(resp.companyName()).isEqualTo(retailer.getCompanyName());
        assertThat(resp.minimumOrder()).isEqualTo(retailer.getMinimumOrder());
        assertThat(resp.description()).isEqualTo(retailer.getDescription());
    }

    @Test
    public void testGetRetailerException() {
        dummy.when(AppUtils::getCurrentlyAuthenticatedUser).thenReturn(new User(1,
                                                                          "Test@test.com",
                                                                                new BCryptPasswordEncoder().encode("TestPW"),
                                                                                new Date(),
                                                                                new Date(),
                                                                                Role.ROLE_CUSTOMER));
        given(retailerRepository.findById(1)).willReturn(Optional.empty());

        assertThatThrownBy(() -> retailerService.getRetailer())
                .isInstanceOf(FDApiException.class)
                .hasMessageContaining("Retailer does not exist");
    }

    @Test
    public void testGetRetailerAddress() {
        Retailer retailer = createTestRetailer(true);
        dummy.when(AppUtils::getCurrentlyAuthenticatedUser).thenReturn(new User(1,
                                                                          "Test@test.com",
                                                                                new BCryptPasswordEncoder().encode("TestPW"),
                                                                                new Date(),
                                                                                new Date(),
                                                                                Role.ROLE_CUSTOMER));

        given(retailerRepository.findById(1)).willReturn(Optional.of(retailer));

        GetRetailerAddressResponse resp = retailerService.getRetailerAddress();
        assertThat(resp).isNotNull();
        assertThat(resp.settlement()).isEqualTo("Budapest");
    }

    @Test
    public void testGetRetailerAddressEmptyResp() {
        Retailer retailer = createTestRetailer(false);
        dummy.when(AppUtils::getCurrentlyAuthenticatedUser).thenReturn(new User(1,
                                                                          "Test@test.com",
                                                                                new BCryptPasswordEncoder().encode("TestPW"),
                                                                                new Date(),
                                                                                new Date(),
                                                                                Role.ROLE_CUSTOMER));

        given(retailerRepository.findById(1)).willReturn(Optional.of(retailer));

        GetRetailerAddressResponse resp = retailerService.getRetailerAddress();
        assertThat(resp).isNotNull();
        assertThat(resp.settlement()).isEqualTo("");
    }

    @Test
    public void TestUpdateRetailer() {
        ArgumentCaptor<Retailer> arg = ArgumentCaptor.forClass(Retailer.class);
        Retailer retailer = createTestRetailer(false);
        Retailer savedRetailer;
        UpdateRetailerRequest request = new UpdateRetailerRequest();
        request.setRestaurantName("TestRetailer");
        request.setMinimumOrder(new BigDecimal(4000));
        request.setDescription("TestDesc");
        dummy.when(AppUtils::getCurrentlyAuthenticatedUser).thenReturn(new User(1,
                                                                          "Test@test.com",
                                                                                new BCryptPasswordEncoder().encode("TestPW"),
                                                                                new Date(),
                                                                                new Date(),
                                                                                Role.ROLE_CUSTOMER));
        given(retailerRepository.findById(1)).willReturn(Optional.of(retailer));

        UpdateRetailerResponse resp = retailerService.updateRetailer(request);
        verify(retailerRepository).save(arg.capture());
        savedRetailer = arg.getValue();
        assertThat(savedRetailer.getRestaurantName()).isEqualTo(request.getRestaurantName());
        assertThat(savedRetailer.getMinimumOrder()).isEqualTo(request.getMinimumOrder());
        assertThat(savedRetailer.getDescription()).isEqualTo(request.getDescription());
        assertThat(resp).isNotNull();
    }

    @Test
    public void TestUpdateRetailerAddress() {
        updateRetailerAddress(false);
    }

    @Test
    public void TestUpdateRetailerAddress2() {
        updateRetailerAddress(true);
    }

    private void updateRetailerAddress(boolean isAddressCreated) {
        ArgumentCaptor<Retailer> arg = ArgumentCaptor.forClass(Retailer.class);
        Retailer retailer = createTestRetailer(isAddressCreated);
        Retailer savedRetailer;
        UpdateRetailerAddressRequest request = new UpdateRetailerAddressRequest();
        request.setSettlement("Debrecen");
        request.setZipCode(2222);
        request.setStreetName("Gáz utca");
        request.setStreetNumber("20");
        dummy.when(AppUtils::getCurrentlyAuthenticatedUser).thenReturn(new User(1,
                                                                          "Test@test.com",
                                                                                new BCryptPasswordEncoder().encode("TestPW"),
                                                                                new Date(),
                                                                                new Date(),
                                                                                Role.ROLE_CUSTOMER));
        given(retailerRepository.findById(1)).willReturn(Optional.of(retailer));

        UpdateRetailerAddressResponse resp = retailerService.updateAddress(request);
        verify(retailerRepository).save(arg.capture());
        savedRetailer = arg.getValue();
        assertThat(savedRetailer.getAddress().getSettlement()).isEqualTo(request.getSettlement());
        assertThat(savedRetailer.getAddress().getZipCode()).isEqualTo(request.getZipCode());
        assertThat(savedRetailer.getAddress().getStreetName()).isEqualTo(request.getStreetName());
        assertThat(savedRetailer.getAddress().getStreetNumber()).isEqualTo(request.getStreetNumber());
        assertThat(resp).isNotNull();
    }
}
