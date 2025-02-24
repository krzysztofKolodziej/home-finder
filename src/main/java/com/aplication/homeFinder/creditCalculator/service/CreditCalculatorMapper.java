package com.aplication.homeFinder.creditCalculator.service;

import com.aplication.homeFinder.creditCalculator.model.CreditCalculator;
import com.aplication.homeFinder.creditCalculator.service.dto.CreditCalculatorDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditCalculatorMapper {

    public CreditCalculator toCreditCalculator(CreditCalculatorDto creditCalculatorDto);
}
