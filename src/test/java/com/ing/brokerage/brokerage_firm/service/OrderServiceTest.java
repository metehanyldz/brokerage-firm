package com.ing.brokerage.brokerage_firm.service;

import com.ing.brokerage.brokerage_firm.constants.Side;
import com.ing.brokerage.brokerage_firm.constants.Status;
import com.ing.brokerage.brokerage_firm.exceptions.BaseException;
import com.ing.brokerage.brokerage_firm.model.Asset;
import com.ing.brokerage.brokerage_firm.model.Customer;
import com.ing.brokerage.brokerage_firm.model.Order;
import com.ing.brokerage.brokerage_firm.repository.OrderRepository;
import com.ing.brokerage.brokerage_firm.request.CreateOrderRequest;
import com.ing.brokerage.brokerage_firm.util.AssetUtil;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private AssetService assetService;
    @Mock
    private CustomerService customerService;

    @InjectMocks
    private OrderService orderService;

    private String customerId = "0ab13a37-2709-4f60-970b-b9abf3706d29";
    private String goldAssetName = "gold";
    private BigDecimal size = BigDecimal.valueOf(100);
    private BigDecimal price = BigDecimal.valueOf(10);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetOrderListForCustomer_noExistingCustomer() {
        String exceptionMessage = "Customer not found for id: " + customerId;

        when(customerService.getCustomer(customerId)).thenReturn(null);
        Exception exception = assertThrows(BaseException.class, () ->
                orderService.getOrderListForCustomer(customerId));

        assertEquals(HttpStatus.NOT_FOUND, ((BaseException) exception).getStatus());
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    public void testGetOrderListForCustomer() {
        Order order = Order.builder().orderSide(Side.BUY).assetName(goldAssetName)
                .size(size).price(price).build();
        Customer customer = Customer.builder().orders(List.of(order)).build();

        when(customerService.getCustomer(customerId)).thenReturn(customer);
        List<Order> result = orderService.getOrderListForCustomer(customerId);

        assertNotNull(result);
        assertEquals(result.get(0), order);
    }

    @Test
    public void testDeleteOrder_success() {
        String orderId = "123";
        Customer customer = Customer.builder().id(customerId).build();
        Order order = Order.builder().status(Status.PENDING).orderSide(Side.SELL).assetName(goldAssetName)
                .id(orderId).customer(customer).size(size).build();


        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Asset asset = Asset.builder().assetName(goldAssetName).size(size).usableSize(BigDecimal.ZERO).build();
        when(assetService.getAsset(customerId, goldAssetName)).thenReturn(asset);

        orderService.deleteOrder(orderId);

        verify(orderRepository, times(1)).save(order);
        verify(assetService, times(1)).updateAsset(asset);
        assertEquals(Status.CANCELLED, order.getStatus());
        assertEquals(size, asset.getUsableSize());
    }

    @Test
    public void testDeleteOrder_noExistingOrder() {
        String orderId = "123";
        String exceptionMessage = "Order not found for orderId: " + orderId;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(BaseException.class, () ->
                orderService.deleteOrder(orderId));

        assertEquals(HttpStatus.NOT_FOUND, ((BaseException) exception).getStatus());
        assertEquals(exceptionMessage, exception.getMessage());
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    public void testDeleteOrder_orderStatusNotPending() {
        String orderId = "123";
        String exceptionMessage = "Order not found for orderId: " + orderId;
        Order order = Order.builder().status(Status.MATCHED).build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        Exception exception = assertThrows(BaseException.class, () ->
                orderService.deleteOrder(orderId));

        assertEquals(HttpStatus.NOT_FOUND, ((BaseException) exception).getStatus());
        assertEquals(exceptionMessage, exception.getMessage());
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    public void testCreateOrder() {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setCustomerId(customerId);
        request.setOrderSide(Side.BUY);
        request.setAssetName(goldAssetName);
        request.setSize(size);
        request.setPrice(price);

        Asset asset = Asset.builder().assetName(AssetUtil.MONEY_ASSET).usableSize(request.getTotalPrice())
                .build();
        when(assetService.getAsset(customerId, AssetUtil.MONEY_ASSET)).thenReturn(asset);
        Order order = new Order();
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.createOrder(request);

        assertNotNull(result);
        assertEquals(asset.getUsableSize(), BigDecimal.ZERO);
        verify(assetService, times(1)).updateAsset(asset);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void testCreateOrder_insufficientFunds() {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setCustomerId(customerId);
        request.setOrderSide(Side.BUY);
        request.setAssetName(goldAssetName);
        request.setSize(size);
        request.setPrice(price);

        Asset asset = Asset.builder().assetName(AssetUtil.MONEY_ASSET)
                .usableSize(request.getTotalPrice().subtract(BigDecimal.TEN)).build();
        when(assetService.getAsset(customerId, AssetUtil.MONEY_ASSET)).thenReturn(asset);

        BaseException exception = assertThrows(BaseException.class, () -> {
            orderService.createOrder(request);
        });

        assertEquals(HttpStatus.NOT_ACCEPTABLE, exception.getStatus());
        assertTrue(exception.getMessage().contains("insufficient for order request"));
        verify(orderRepository, never()).save(any(Order.class));
    }
}
