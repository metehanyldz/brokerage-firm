package com.ing.brokage.brokage_firm.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AssetId implements Serializable {
    private String customerId;

    private String assetName;
}
