package com.ing.brokerage.brokerage_firm.repository;

import com.ing.brokerage.brokerage_firm.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
