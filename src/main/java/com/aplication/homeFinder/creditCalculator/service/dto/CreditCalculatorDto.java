package com.aplication.homeFinder.creditCalculator.service.dto;

import com.aplication.homeFinder.creditCalculator.service.SourceOfIncome;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditCalculatorDto {

    @Min(value = 0, message = "Value must be greater than or equal to zero")
    private int propertyValue;
    @Min(value = 0, message = "Value must be greater than or equal to zero")
    private int downPayment;
    @Min(value = 0, message = "Value must be greater than or equal to zero")
    private int creditAmount;
    @Min(value = 1, message = "Value must be greater than or equal to one")
    private int repaymentPeriod;
    @NotNull(message = "Value must not be null")
    private SourceOfIncome sourceOfIncome;
    @Min(value = 33, message = "Value must be greater than or equal to thirty-three")
    private int contractDurationInMonth;
    private boolean continuityOfEmployment;
    @Min(value = 0, message = "Value must be greater than or equal to zero")
    private int monthlyNetIncome;
    @Min(value = 0, message = "Value must be greater than or equal to zero")
    private int monthlyExpenditures;
    private boolean delayInLoanRepayment;
    @Min(value = 0, message = "Value must be greater than or equal to zero")
    private int numberOfDependents;
    @Min(value = 0, message = "Value must be greater than or equal to zero")
    private int monthlyAmountOtherLoans;
    @Min(value = 0, message = "Value must be greater than or equal to zero")
    private int creditCardLimit;
    @NotNull(message = "Value must not be null")
    @Past(message = "Date must be past")
    private LocalDate dateOfBirth;
    @NotNull(message = "Value must not be null")
    @Pattern(regexp = "^\\d{9}$", message = "Invalid phone number")
    private String phoneNumber;
    @NotBlank(message = "Value must not be empty")
    private String name;
}
