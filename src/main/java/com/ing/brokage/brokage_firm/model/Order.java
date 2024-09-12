package com.ing.brokage.brokage_firm.model;

import com.ing.brokage.brokage_firm.constants.Side;
import com.ing.brokage.brokage_firm.constants.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @EmbeddedId
    private OrderId orderId;

    private Side orderSide;

    private BigDecimal size;

    private BigDecimal price;

    private Status status;

    @CreationTimestamp
    private LocalDateTime createDate;
}
