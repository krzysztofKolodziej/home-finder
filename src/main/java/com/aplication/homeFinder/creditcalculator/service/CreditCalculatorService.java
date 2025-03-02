package com.aplication.homeFinder.creditcalculator.service;

import com.aplication.homeFinder.creditcalculator.model.CreditCalculator;
import com.aplication.homeFinder.creditcalculator.repository.CreditCalculatorRepository;
import com.aplication.homeFinder.creditcalculator.service.dto.CreditCalculatorDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CreditCalculatorService {

    private final CreditCalculatorRepository creditCalculatorRepository;
    private final Calculator calculator;
    private final CreditCalculatorMapper creditCalculatorMapper;


    public int checkCreditWorthiness(CreditCalculatorDto creditCalculatorDto) {
        return calculator.maxLoanAmount(creditCalculatorDto);
    }

    public CreditCalculator saveCalculationWorthiness(CreditCalculatorDto creditCalculatorDto) {
        return creditCalculatorRepository.save(creditCalculatorMapper.toCreditCalculator(creditCalculatorDto));
    }
}

