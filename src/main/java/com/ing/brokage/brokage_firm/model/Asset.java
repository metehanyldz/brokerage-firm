package com.ing.brokage.brokage_firm.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "assets")
@IdClass(AssetId.class)
public class Asset {
    @Id
    private String customerId;
    @Id
    private String assetName;

    private BigDecimal size;

    private BigDecimal usableSize;
}
