package com.ing.brokage.brokage_firm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssetId implements Serializable {
    private String customerId;

    private String assetName;
}
