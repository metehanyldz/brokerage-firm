package com.ing.brokage.brokage_firm.controller;

import com.ing.brokage.brokage_firm.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/money")
public class MoneyController {
    private static final String TRY_ASSET = "TRY";

    @Autowired
    AssetService assetService;
    @PostMapping("/deposit")
    public ResponseEntity<Boolean> deposit(@RequestParam String customerId, BigDecimal size) {
        return ResponseEntity.ok(assetService.createAsset(customerId, TRY_ASSET, size));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestParam(required = false) String message) {
        return null;
    }
}
