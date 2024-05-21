package com.aplication.homeFinder.creditCalculator.service;


import com.aplication.homeFinder.creditCalculator.SourceOfIncome;

public class Calculator {

    private static final double MAX_DTI = 0.6;
    private static final double averageAnnualInterestRateLoan = 0.0821;

    public int maxLoanAmount(int monthlyNetIncome, int monthlyExpenditures, int numberOfDependents,
                             int monthlyAmountOtherLoans, int creditCardLimit, int repaymentPeriod,
                             SourceOfIncome sourceOfIncome, int contractDurationInMonth, boolean continuityOfEmployment) {
        double monthlyInterestRate = averageAnnualInterestRateLoan / 12;
        double numberOfPayments = repaymentPeriod * 12;

        double creditWorthiness = (availableIncomeForDebt(monthlyNetIncome, monthlyExpenditures, numberOfDependents,
                monthlyAmountOtherLoans, creditCardLimit, sourceOfIncome, contractDurationInMonth, continuityOfEmployment)
                * (Math.pow(1 + monthlyInterestRate, numberOfPayments) - 1))
                / (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfPayments));

        double result = 0;

        if (sourceOfIncome == SourceOfIncome.FIXED_TERM_CONTRACT){
            if (contractDurationInMonth <= 12) {
                creditWorthiness = 0;
            } else {
                result =  0.14 + ((contractDurationInMonth - 12.0) /100.0);
                creditWorthiness = (int) (result * creditWorthiness);
            }
        }
        return (int) creditWorthiness;
    }

    private double availableIncomeForDebt(int monthlyNetIncome, int monthlyExpenditures, int numberOfDependents,
                                          int monthlyAmountOtherLoans, int creditCardLimit, SourceOfIncome sourceOfIncome,
                                          int contractDurationInMonth, boolean continuityOfEmployment) {
        int validIncome = validationIncome(sourceOfIncome, monthlyNetIncome, contractDurationInMonth, continuityOfEmployment);

        double result = validIncome * MAX_DTI - totalMonthlyDebts(monthlyExpenditures, monthlyAmountOtherLoans,
                creditCardLimit, numberOfDependents, monthlyNetIncome);
        if (result <= 0) {
            return 0;
        }
        return result;
    }

    private int validationIncome(SourceOfIncome sourceOfIncome, int monthlyNetIncome, int contractDurationInMonth,
                                 boolean continuityOfEmployment) {
        double result = 0;

        switch (sourceOfIncome) {
            case BUSINESS_OPERATION_LESS_THAN_YEAR:
                monthlyNetIncome = 0;
                break;
            case CONTRACT_OF_SPECIFIC_WORK:
                if (!continuityOfEmployment) {
                    monthlyNetIncome = 0;
                }
                break;
            case ORDER_CONTRACT:
                if (!continuityOfEmployment) {
                    monthlyNetIncome = 0;
                }
                break;
            case ANNUITIES:
                if (!continuityOfEmployment) {
                    monthlyNetIncome = 0;
                }
                break;
        }
        return monthlyNetIncome;
    }

    private int totalMonthlyDebts(int monthlyExpenditures, int monthlyAmountOtherLoans, int creditCardLimit,
                                  int numberOfDependents, int monthlyNetIncome) {
        int debts = monthlyExpenditures + monthlyAmountOtherLoans + (int) (creditCardLimit * 0.02);

        if (debts >= monthlyNetIncome) {
            return debts;
        }
        switch (numberOfDependents) {
            case 0:
                monthlyExpenditures = 1000;
                break;
            case 1:
                monthlyExpenditures = 1500;
                break;
            case 2:
                monthlyExpenditures = 2000;
                break;
            case 3:
                monthlyExpenditures = 2500;
                break;
            case 4:
                monthlyExpenditures = 3000;
                break;
            case 5:
                monthlyExpenditures = 3500;
                break;
            case 6:
                monthlyExpenditures = 3500;
                break;
            default:
                monthlyExpenditures = 4000;
        }
        return monthlyExpenditures + monthlyAmountOtherLoans + (int) (creditCardLimit * 0.02);
    }


}
