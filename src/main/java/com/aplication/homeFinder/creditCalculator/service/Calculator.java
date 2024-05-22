package com.aplication.homeFinder.creditCalculator.service;


import com.aplication.homeFinder.creditCalculator.service.dto.CreditCalculatorDto;

public class Calculator {

    private static final double MAX_DTI = 0.6;
    private static final double averageAnnualInterestRateLoan = 0.0821;

    public int maxLoanAmount(CreditCalculatorDto creditCalculatorDto) {
        double monthlyInterestRate = averageAnnualInterestRateLoan / 12;
        double numberOfPayments = creditCalculatorDto.getRepaymentPeriod() * 12;

        if (creditCalculatorDto.isDelayInLoanRepayment()) {
            return 0;
        }
        double creditWorthiness = (availableIncomeForDebt(creditCalculatorDto)
                * (Math.pow(1 + monthlyInterestRate, numberOfPayments) - 1))
                / (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfPayments));

        double result = 0;

        if (creditCalculatorDto.getSourceOfIncome() == SourceOfIncome.FIXED_TERM_CONTRACT) {
            if (creditCalculatorDto.getContractDurationInMonth() <= 12) {
                creditWorthiness = 0;
            } else {
                result = 0.14 + ((creditCalculatorDto.getContractDurationInMonth() - 12.0) / 100.0);
                creditWorthiness = (int) (result * creditWorthiness);
            }
        }
        return (int) creditWorthiness;
    }

    private double availableIncomeForDebt(CreditCalculatorDto creditCalculatorDto) {
        int validIncome = validationIncome(creditCalculatorDto);

        double result = validIncome * MAX_DTI - totalMonthlyDebts(creditCalculatorDto);

        if (result <= 0) {
            return 0;
        }
        return result;
    }

    private int validationIncome(CreditCalculatorDto creditCalculatorDto) {
        int monthlyNetIncome = creditCalculatorDto.getMonthlyNetIncome();

        switch (creditCalculatorDto.getSourceOfIncome()) {
            case BUSINESS_OPERATION_LESS_THAN_YEAR:
                monthlyNetIncome = 0;
                break;
            case CONTRACT_OF_SPECIFIC_WORK, ORDER_CONTRACT, ANNUITIES:
                if (!creditCalculatorDto.isContinuityOfEmployment()) {
                    monthlyNetIncome = 0;
                }
                break;
        }
        return monthlyNetIncome;
    }

    private int totalMonthlyDebts(CreditCalculatorDto creditCalculatorDto) {
        int monthlyExpenditures = creditCalculatorDto.getMonthlyExpenditures();
        int monthlyAmountOtherLoans = creditCalculatorDto.getMonthlyAmountOtherLoans();
        int creditCardLimit = creditCalculatorDto.getCreditCardLimit();

        int debts = monthlyExpenditures + monthlyAmountOtherLoans + (int) (creditCardLimit * 0.02);

        if (debts >= creditCalculatorDto.getMonthlyNetIncome()) {
            return debts;
        }
        switch (creditCalculatorDto.getNumberOfDependents()) {
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
                monthlyExpenditures = 3250;
                break;
            case 6:
                monthlyExpenditures = 3500;
                break;
            default:
                monthlyExpenditures = 3750;
        }
        return monthlyExpenditures + monthlyAmountOtherLoans + (int) (creditCardLimit * 0.02);
    }


}
