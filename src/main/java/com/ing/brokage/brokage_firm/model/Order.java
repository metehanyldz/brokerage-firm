package com.ing.brokage.brokage_firm.model;

import com.ing.brokage.brokage_firm.constants.Side;
import com.ing.brokage.brokage_firm.constants.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    private String customerId;

    private String assetName;

    private Side orderSide;

    private BigDecimal size;

    private BigDecimal price;

    private Status status;

    private LocalDateTime createDate;
}
