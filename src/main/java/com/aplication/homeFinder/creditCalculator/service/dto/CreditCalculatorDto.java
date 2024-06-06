package com.aplication.homeFinder.creditCalculator.service.dto;

import com.aplication.homeFinder.creditCalculator.service.SourceOfIncome;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
public class CreditCalculatorDto {

    @Min(0)
    private int propertyValue;
    @Min(0)
    private int downPayment;
    @Min(0)
    private int creditAmount;
    @Min(1)
    private int repaymentPeriod;
    @NotNull
    private SourceOfIncome sourceOfIncome;
    @Max(33)
    @Min(0)
    private int contractDurationInMonth;
    private boolean continuityOfEmployment;
    @Min(0)
    private int monthlyNetIncome;
    @Min(0)
    private int monthlyExpenditures;
    private boolean delayInLoanRepayment;
    @Min(0)
    private int numberOfDependents;
    @Min(0)
    private int monthlyAmountOtherLoans;
    @Min(0)
    private int creditCardLimit;
    @NotNull
    @Past
    private LocalDate dateOfBirth;
    @Pattern(regexp = "^\\d{9}$", message = "Nieprawidłowy numer telefonu")
    private String phoneNumber;
    @NotBlank
    private String name;
}
