package com.aplication.homeFinder.creditCalculator.controller;

import com.aplication.homeFinder.creditCalculator.service.dto.CreditCalculatorDto;
import com.aplication.homeFinder.creditCalculator.service.CreditCalculatorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
public class CreditCalculatorController {

    private final CreditCalculatorService creditCalculatorService;

    @PostMapping("/creditCalculator")
    public ResponseEntity<?> checkCreditWorthiness(@RequestBody @Valid CreditCalculatorDto creditCalculatorDto) {
        try {
            int creditWorthiness = creditCalculatorService.checkCreditWorthiness(creditCalculatorDto);
            return ResponseEntity.status(HttpStatus.OK).body(creditWorthiness);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }
}
