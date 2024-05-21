package com.aplication.homeFinder.creditCalculator;

public class ValidationData  {

    public boolean isCreditPossible(boolean delayInLoanRepayment, int monthlyAmountOtherLoans, int creditCardLimit,
                                    int creditAmount, int repaymentPeriod, int monthlyNetIncome) {
        if (delayInLoanRepayment || DtiRate.calculateDebtToIncomeRatio(monthlyAmountOtherLoans, creditCardLimit,
                creditAmount, repaymentPeriod, monthlyNetIncome) < 0.4) {
            return false;
        } else return true;
    }
}
