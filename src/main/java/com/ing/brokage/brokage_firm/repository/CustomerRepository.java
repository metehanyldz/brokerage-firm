package com.ing.brokage.brokage_firm.repository;

import com.ing.brokage.brokage_firm.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
