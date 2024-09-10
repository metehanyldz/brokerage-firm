package com.ing.brokage.brokage_firm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/money")
public class MoneyController {

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestParam(required = false) String message) {
        return null;
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestParam(required = false) String message) {
        return null;
    }
}
