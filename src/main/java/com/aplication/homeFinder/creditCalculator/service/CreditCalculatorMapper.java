package com.aplication.homeFinder.creditCalculator.service;

import com.aplication.homeFinder.creditCalculator.model.CreditCalculator;
import com.aplication.homeFinder.creditCalculator.service.dto.CreditCalculatorDto;

public class CreditCalculatorMapper {
     CreditCalculator mapper(CreditCalculatorDto creditCalculatorDto) {
        return CreditCalculator.builder()
                .id(null)
                .propertyValue(creditCalculatorDto.getPropertyValue())
                .downPayment(creditCalculatorDto.getDownPayment())
                .creditAmount(creditCalculatorDto.getCreditAmount())
                .repaymentPeriod(creditCalculatorDto.getRepaymentPeriod())
                .sourceOfIncome(creditCalculatorDto.getSourceOfIncome())
                .contractDurationInMonth(creditCalculatorDto.getContractDurationInMonth())
                .continuityOfEmployment(creditCalculatorDto.isContinuityOfEmployment())
                .monthlyNetIncome(creditCalculatorDto.getMonthlyNetIncome())
                .monthlyExpenditures(creditCalculatorDto.getMonthlyExpenditures())
                .delayInLoanRepayment(creditCalculatorDto.isDelayInLoanRepayment())
                .numberOfDependents(creditCalculatorDto.getNumberOfDependents())
                .monthlyAmountOtherLoans(creditCalculatorDto.getMonthlyAmountOtherLoans())
                .creditCardLimit(creditCalculatorDto.getCreditCardLimit())
                .dateOfBirth(creditCalculatorDto.getDateOfBirth())
                .phoneNumber(creditCalculatorDto.getPhoneNumber())
                .name(creditCalculatorDto.getName())
                .build();
    }
}
