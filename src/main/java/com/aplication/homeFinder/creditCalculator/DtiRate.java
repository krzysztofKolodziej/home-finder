package com.aplication.homeFinder.creditCalculator;

public class DtiRate {
    private static final double averageAnnualInterestRateLoan = 0.0821;
    private static final double averageAnnualInterestRateCreditCard = 0.2;


    private static double calculateMonthlyInstallment(int creditAmount, int repaymentPeriod) {
        double monthlyInterestRate = averageAnnualInterestRateLoan / 12;
        double numberOfPayments = repaymentPeriod * 12;

        return (creditAmount * (1 + Math.pow(monthlyInterestRate, numberOfPayments)) * monthlyInterestRate)
                / (1 + Math.pow(monthlyInterestRate, numberOfPayments) - 1);
    }

    static double calculateDebtToIncomeRatio(int monthlyAmountOtherLoans, int creditCardLimit, int creditAmount,
                                      int repaymentPeriod, int monthlyNetIncome ) {
        double monthlyInterestRateCreditCard = averageAnnualInterestRateCreditCard / 12;

        return (calculateMonthlyInstallment(creditAmount, repaymentPeriod) + monthlyAmountOtherLoans
                + (creditCardLimit * monthlyInterestRateCreditCard))
                / monthlyNetIncome;
    }
}
