package com.ing.brokerage.brokerage_firm.service;

import com.ing.brokerage.brokerage_firm.model.Customer;
import com.ing.brokerage.brokerage_firm.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    private PasswordEncoder passwordEncoder;

    public Customer signupCustomer(String username, String password){
        return customerRepository.save(Customer.builder().userName(username)
                .passwordHash(passwordEncoder.encode(password)).build());
    }

    public Customer getCustomer(String customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }
}
