package com.ing.brokage.brokage_firm.service;

import com.ing.brokage.brokage_firm.model.Asset;
import com.ing.brokage.brokage_firm.model.AssetId;
import com.ing.brokage.brokage_firm.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AssetService {
    @Autowired
    private AssetRepository assetRepository;

    private static final String TRY_ASSET = "TRY";
    public Asset depositMoneyAsset(String customerId, BigDecimal size){
        AssetId assetId = AssetId.builder().customerId(customerId).assetName(TRY_ASSET).build();
        Asset asset = assetRepository.findById(assetId).orElse(null);
        if (asset != null) {
            asset.deposit(size);
            return assetRepository.save(asset);
        }
        return this.createAsset(customerId, TRY_ASSET, size);
    }

    public Asset withdrawMoneyAsset(String customerId, BigDecimal size){
        AssetId assetId = AssetId.builder().customerId(customerId).assetName(TRY_ASSET).build();
        Asset asset = assetRepository.getReferenceById(assetId);
        if (asset != null && asset.withdraw(size)) {
            return assetRepository.save(asset);
        }
        return null;
    }
    public Asset createAsset(String customerId, String assetName, BigDecimal size) {
        Asset asset = Asset.builder().customerId(customerId).assetName(assetName).size(size).usableSize(size).build();
        return assetRepository.save(asset);
    }
    public boolean updateAsset(String customerId, String assetName, BigDecimal size, BigDecimal usableSize) {
        AssetId assetId = AssetId.builder().customerId(customerId).assetName(assetName).build();
        Asset asset = assetRepository.getReferenceById(assetId);
        asset.setSize(size);
        asset.setUsableSize(usableSize);
        return (assetRepository.save(asset) != null);
    }

    public List<Asset> listAssets(String customerId) {
        Example<Asset> assetExample = Example.of(Asset.builder().customerId(customerId).build());
        return assetRepository.findAll(assetExample);
    }
}
