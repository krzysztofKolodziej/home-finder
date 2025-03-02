package com.aplication.homeFinder.creditcalculator.service;

import com.aplication.homeFinder.creditcalculator.model.CreditCalculator;
import com.aplication.homeFinder.creditcalculator.service.dto.CreditCalculatorDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditCalculatorMapper {

    public CreditCalculator toCreditCalculator(CreditCalculatorDto creditCalculatorDto);
}
