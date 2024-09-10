package com.ing.brokage.brokage_firm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assets")
public class AssetController {

    @GetMapping("/list")
    public ResponseEntity<String> listOrders(@RequestParam(required = false) String message) {
        return null;
    }
}
