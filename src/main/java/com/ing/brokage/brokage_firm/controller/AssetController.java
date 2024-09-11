package com.ing.brokage.brokage_firm.controller;

import com.ing.brokage.brokage_firm.model.Asset;
import com.ing.brokage.brokage_firm.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;
    @GetMapping("/list")
    public ResponseEntity<List<Asset>> listAssets(@RequestParam(required = true) String customerId) {
        return ResponseEntity.ok(assetService.listAssets(customerId));
    }
}
