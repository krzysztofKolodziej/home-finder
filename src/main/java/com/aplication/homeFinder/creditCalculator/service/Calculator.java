package com.aplication.homeFinder.creditCalculator.service;


import com.aplication.homeFinder.creditCalculator.service.dto.CreditCalculatorDto;

public class Calculator {

    private static final double MAX_DTI = 0.6;
    private static final double AVERAGE_ANNUAL_INTEREST_RATE_LOAN = 0.0821;
    private static final double MONTHLY_COST_CREDIT_CARD = 0.02;

    public int maxLoanAmount(CreditCalculatorDto creditCalculatorDto) {

        double monthlyInterestRate = AVERAGE_ANNUAL_INTEREST_RATE_LOAN / 12;
        double numberOfPayments = creditCalculatorDto.getRepaymentPeriod() * 12;
        int contractDurationInMont = creditCalculatorDto.getContractDurationInMonth();
        double result;

        if (creditCalculatorDto.isDelayInLoanRepayment()) {
            return 0;
        }
        double creditWorthiness = (availableIncomeForDebt(creditCalculatorDto)
                * (Math.pow(1 + monthlyInterestRate, numberOfPayments) - 1))
                / (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfPayments));

        if (creditCalculatorDto.getSourceOfIncome() == SourceOfIncome.FIXED_TERM_CONTRACT) {
            if (contractDurationInMont <= 12) {
                creditWorthiness = 0;
            } else {
                result = contractDurationInMont / 100.0;
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

        int debts = monthlyExpenditures + monthlyAmountOtherLoans + (int) (creditCardLimit * MONTHLY_COST_CREDIT_CARD);

        if (debts >= creditCalculatorDto.getMonthlyNetIncome()) {
            return debts;
        }
        monthlyExpenditures = switch (creditCalculatorDto.getNumberOfDependents()) {
            case 0 -> 1000;
            case 1 -> 1500;
            case 2 -> 2000;
            case 3 -> 2500;
            case 4 -> 3000;
            case 5 -> 3250;
            case 6 -> 3500;
            default -> 3750;
        };
        return monthlyExpenditures + monthlyAmountOtherLoans + (int) (creditCardLimit * MONTHLY_COST_CREDIT_CARD);
    }


}
