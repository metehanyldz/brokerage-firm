package com.ing.brokerage.brokerage_firm.controller;

import com.ing.brokerage.brokerage_firm.model.Order;
import com.ing.brokerage.brokerage_firm.request.CreateOrderRequest;
import com.ing.brokerage.brokerage_firm.service.OrderService;
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
    public ResponseEntity<ResponseEntity.BodyBuilder> deleteOrder(@RequestParam String orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<Order>> listOrders(@RequestParam String customerId) {
        return ResponseEntity.ok(orderService.getOrderListForCustomer(customerId));
    }
}
