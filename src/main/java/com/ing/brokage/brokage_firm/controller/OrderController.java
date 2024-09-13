package com.ing.brokage.brokage_firm.controller;

import com.ing.brokage.brokage_firm.model.Order;
import com.ing.brokage.brokage_firm.request.CreateOrderRequest;
import com.ing.brokage.brokage_firm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @PostMapping()
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        return ResponseEntity.ok(orderService.createOrder(createOrderRequest));
    }

    @DeleteMapping()
    public ResponseEntity<Boolean> deleteOrder(@RequestParam String orderId) {
        return ResponseEntity.ok(orderService.deleteOrder(orderId));
    }

    @GetMapping()
    public ResponseEntity<List<Order>> listOrders(@RequestParam String customerId) {
        return ResponseEntity.ok(orderService.getOrderListForCustomer(customerId));
    }
}
