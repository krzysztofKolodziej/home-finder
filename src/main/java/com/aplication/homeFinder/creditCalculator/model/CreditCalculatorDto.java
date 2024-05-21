package com.aplication.homeFinder.creditCalculator.model;

import com.aplication.homeFinder.creditCalculator.SourceOfIncome;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
public class CreditCalculatorDto {

    private int propertyValue;
    private int downPayment;
    private int creditAmount;
    private int repaymentPeriod;
    private SourceOfIncome sourceOfIncome;
    @Max(33)
    @Min(0)
    private int contractDurationInMonth;
    private boolean continuityOfEmployment;
    private int monthlyNetIncome;

    private int monthlyExpenditures;
    private boolean delayInLoanRepayment;
    private int numberOfDependents;
    private int monthlyAmountOtherLoans;
    private int creditCardLimit;

    private Date dateOfBirth;
    private int phoneNumber;
    private String name;
}
