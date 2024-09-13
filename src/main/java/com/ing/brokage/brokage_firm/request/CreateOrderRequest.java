package com.ing.brokage.brokage_firm.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ing.brokage.brokage_firm.constants.Side;
import com.ing.brokage.brokage_firm.constants.Status;
import com.ing.brokage.brokage_firm.model.Customer;
import com.ing.brokage.brokage_firm.model.Order;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
public class CreateOrderRequest {
    private String customerId;

    private String assetName;

    private Side orderSide;

    private BigDecimal size;

    private BigDecimal price;

    @JsonIgnore
    public BigDecimal getTotalPrice() {
        return this.getSize().multiply(this.getPrice());
    }

    @JsonIgnore
    public Order toOrder() {
        return Order.builder()
                .customer(new Customer(customerId))
                .assetName(assetName)
                .orderSide(this.orderSide)
                .size(this.size)
                .price(this.price)
                .status(Status.PENDING)
                .build();
    }
}
