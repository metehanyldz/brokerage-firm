package com.ing.brokage.brokage_firm.service;

import com.ing.brokage.brokage_firm.model.Asset;
import com.ing.brokage.brokage_firm.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AssetService {
    @Autowired
    private AssetRepository assetRepository;
    public boolean createAsset(String customerId, String assetName, BigDecimal size) {
        Asset asset = Asset.builder().customerId(customerId).assetName(assetName).size(size).usableSize(size).build();
        return (assetRepository.save(asset) != null);
    }

    public List<Asset> listAssets(String customerId) {
        return null;
    }
}
