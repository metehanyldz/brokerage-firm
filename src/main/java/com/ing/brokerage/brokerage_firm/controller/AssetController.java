package com.ing.brokerage.brokerage_firm.controller;

import com.ing.brokerage.brokerage_firm.model.Asset;
import com.ing.brokerage.brokerage_firm.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;
    @GetMapping("/list")
    public ResponseEntity<List<Asset>> listAssets(@RequestParam String customerId) {
        return ResponseEntity.ok(assetService.listAssets(customerId));
    }

    @PostMapping
    public ResponseEntity<Asset> createAsset(@RequestParam String customerId,
                                             @RequestParam String assetName,
                                             @RequestParam BigDecimal size) {
        return ResponseEntity.ok(assetService.createAsset(customerId, assetName, size));
    }
}
