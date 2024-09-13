package com.ing.brokage.brokage_firm.service;

import com.ing.brokage.brokage_firm.exceptions.BaseException;
import com.ing.brokage.brokage_firm.model.Asset;
import com.ing.brokage.brokage_firm.model.Customer;
import com.ing.brokage.brokage_firm.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.ing.brokage.brokage_firm.util.AssetUtil.MONEY_ASSET;
import static com.ing.brokage.brokage_firm.util.AssetUtil.validateIBAN;

@Service
public class AssetService {
    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private CustomerService customerService;

    public Asset depositMoneyAsset(String customerId, BigDecimal size){
        Asset asset = getMoneyAsset(customerId);
        if (asset != null) {
            asset.deposit(size);
            return assetRepository.save(asset);
        }
        return this.createAsset(customerId, MONEY_ASSET, size);
    }

    public Asset withdrawMoneyAsset(String customerId, BigDecimal size, String iban){
        validateIBAN(iban);
        Asset asset = getMoneyAsset(customerId);
        if (asset != null && asset.withdraw(size)) {
            return assetRepository.save(asset);
        }
        throw new BaseException("Insufficient funds", HttpStatus.NOT_ACCEPTABLE);
    }
    public Asset createAsset(String customerId, String assetName, BigDecimal size) {
        Asset asset = Asset.builder().customer(new Customer(customerId)).assetName(assetName).size(size).usableSize(size).build();
        return assetRepository.save(asset);
    }

    public Asset updateAsset(Asset asset) {
       return assetRepository.save(asset);
    }

    public Asset getAsset(String customerId, String assetName) {
        Asset asset = customerService.getCustomer(customerId).getAssets().stream()
                .filter(customerAsset -> customerAsset.getAssetName().equals(assetName)).findAny().orElse(null);
        return asset;
    }

    public Asset getMoneyAsset(String customerId) {
        return getAsset(customerId, MONEY_ASSET);
    }

    public List<Asset> listAssets(String customerId) {
        Example<Asset> assetExample = Example.of(Asset.builder().customer(new Customer(customerId)).build());
        return assetRepository.findAll(assetExample);
    }
}
