package com.ing.brokage.brokage_firm.controller;

import com.ing.brokage.brokage_firm.model.Customer;
import com.ing.brokage.brokage_firm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("signup")
    public ResponseEntity<Customer> signup(@RequestParam String username,
                                           @RequestParam String password) {
        return ResponseEntity.ok(customerService.signupCustomer(username, password));
    }
}
