package com.aplication.homeFinder.creditcalculator.controller;

import com.aplication.homeFinder.creditcalculator.service.CreditCalculatorService;
import com.aplication.homeFinder.creditcalculator.service.dto.CreditCalculatorDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CreditCalculatorController {

    private final CreditCalculatorService creditCalculatorService;

    @PostMapping("/creditCalculator")
    public ResponseEntity<Integer> checkCreditWorthiness(@RequestBody @Valid CreditCalculatorDto creditCalculatorDto) {
        creditCalculatorService.saveCalculationWorthiness(creditCalculatorDto);
        return ResponseEntity.status(HttpStatus.OK).body(creditCalculatorService.checkCreditWorthiness(creditCalculatorDto));
    }
}
