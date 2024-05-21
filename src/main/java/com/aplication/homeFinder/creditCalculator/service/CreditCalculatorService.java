package com.aplication.homeFinder.creditCalculator.service;

import com.aplication.homeFinder.creditCalculator.model.CreditCalculator;
import com.aplication.homeFinder.creditCalculator.model.CreditCalculatorDto;
import com.aplication.homeFinder.creditCalculator.repository.CreditCalculatorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreditCalculatorService {

    private final CreditCalculatorRepository creditCalculatorRepository;
    private final Calculator calculator = new Calculator();

    public int checkCreditWorthiness(CreditCalculatorDto creditCalculatorDto) {
        creditCalculatorRepository.save(mapper(creditCalculatorDto));

        return calculator.maxLoanAmount(creditCalculatorDto.getMonthlyNetIncome(),
                creditCalculatorDto.getMonthlyExpenditures(),
                creditCalculatorDto.getNumberOfDependents(),
                creditCalculatorDto.getMonthlyAmountOtherLoans(),
                creditCalculatorDto.getCreditCardLimit(),
                creditCalculatorDto.getRepaymentPeriod(),
                creditCalculatorDto.getSourceOfIncome(),
                creditCalculatorDto.getContractDurationInMonth(),
                creditCalculatorDto.isContinuityOfEmployment());
    }

    private CreditCalculator mapper(CreditCalculatorDto creditCalculatorDto) {
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

