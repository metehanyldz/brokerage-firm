package com.ing.brokage.brokage_firm.service;

import com.ing.brokage.brokage_firm.model.Customer;
import com.ing.brokage.brokage_firm.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer signupCustomer(String username, String password){
        return customerRepository.save(Customer.builder().userName(username).passwordHash(password).build());
    }
}
