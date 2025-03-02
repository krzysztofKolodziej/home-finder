package com.aplication.homeFinder.creditcalculator.model;

import com.aplication.homeFinder.creditcalculator.service.SourceOfIncome;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditCalculator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int propertyValue;

    @Column(nullable = false)
    private int downPayment;

    @Column(nullable = false)
    private int creditAmount;

    @Column(nullable = false)
    private int repaymentPeriod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SourceOfIncome sourceOfIncome;

    private int contractDurationInMonth;

    private boolean continuityOfEmployment;

    @Column(nullable = false)
    private int monthlyNetIncome;

    @Column(nullable = false)
    private int monthlyExpenditures;

    @Column(nullable = false)
    private boolean delayInLoanRepayment;

    @Column(nullable = false)
    private int numberOfDependents;

    @Column(nullable = false)
    private int monthlyAmountOtherLoans;

    @Column(nullable = false)
    private int creditCardLimit;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String name;
}
