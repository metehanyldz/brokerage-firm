package com.ing.brokage.brokage_firm.repository;

import com.ing.brokage.brokage_firm.model.Order;
import com.ing.brokage.brokage_firm.model.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, OrderId> {
}
