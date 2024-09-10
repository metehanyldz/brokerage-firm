package com.ing.brokage.brokage_firm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @PostMapping()
    public ResponseEntity<String> createOrder(@RequestParam(required = false) String message) {
        return null;
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteOrder(@RequestParam(required = false) String message) {
        return null;
    }

    @GetMapping()
    public ResponseEntity<String> listOrders(@RequestParam(required = false) String message) {
        return null;
    }
}
