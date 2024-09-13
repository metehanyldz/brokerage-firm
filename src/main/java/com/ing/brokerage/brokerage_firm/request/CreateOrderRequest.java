package com.ing.brokerage.brokerage_firm.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ing.brokerage.brokerage_firm.constants.Side;
import com.ing.brokerage.brokerage_firm.constants.Status;
import com.ing.brokerage.brokerage_firm.model.Customer;
import com.ing.brokerage.brokerage_firm.model.Order;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
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

    @Override
    public String toString() {
        return String.format("customerId: %s assetName: %s orderSide: %s size: %s price: %s",
                customerId, assetName, orderSide.name(), size, price);
    }
}
