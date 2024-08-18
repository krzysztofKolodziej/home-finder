package com.aplication.homeFinder.creditCalculator.service;

import com.aplication.homeFinder.creditCalculator.service.dto.CreditCalculatorDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CalculatorTest {

    @InjectMocks
    private Calculator calculator;

    @Test
    void shouldReturnZeroWhenDelayInLoanRepaymentIsTrue() {
        CreditCalculatorDto creditCalculatorDtoTest = getCreditCalculatorDto();
        creditCalculatorDtoTest.setDelayInLoanRepayment(true);
        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertEquals(0, result);
    }

    @Test
    void shouldReturnCreditWorthinessWhenFixedTermContractIsGreaterThanTwelve() {
        CreditCalculatorDto creditCalculatorDtoTest = getCreditCalculatorDto();
        creditCalculatorDtoTest.setSourceOfIncome(SourceOfIncome.FIXED_TERM_CONTRACT);
        creditCalculatorDtoTest.setContractDurationInMonth(13);
        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertNotEquals(0,result);
    }

    @Test
    void shouldReturnZeroWhenFixedTermContractIsShorterOrEqualsTwelve() {
        CreditCalculatorDto creditCalculatorDtoTest = getCreditCalculatorDto();
        creditCalculatorDtoTest.setSourceOfIncome(SourceOfIncome.FIXED_TERM_CONTRACT);
        creditCalculatorDtoTest.setContractDurationInMonth(12);
        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertEquals(0, result);
    }

    @Test
    void shouldReturnZeroWhenSourceOfIncomeEqualsBusinessOperationLessThanYear() {
        CreditCalculatorDto creditCalculatorDtoTest = getCreditCalculatorDto();
        creditCalculatorDtoTest.setSourceOfIncome(SourceOfIncome.BUSINESS_OPERATION_LESS_THAN_YEAR);
        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertEquals(0, result);
    }

    @Test
    void shouldReturnZeroWhenSourceOfIncomeEqualsContractOfSpecificWorkAndContinuityOfEmploymentEqualsFalse() {
        CreditCalculatorDto creditCalculatorDtoTest = getCreditCalculatorDto();
        creditCalculatorDtoTest.setSourceOfIncome(SourceOfIncome.CONTRACT_OF_SPECIFIC_WORK);
        creditCalculatorDtoTest.setContinuityOfEmployment(false);
        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertEquals(0, result);
    }

    @Test
    void shouldReturnZeroWhenSourceOfIncomeEqualsOrderContractAndContinuityOfEmploymentEqualsFalse() {
        CreditCalculatorDto creditCalculatorDtoTest = getCreditCalculatorDto();
        creditCalculatorDtoTest.setSourceOfIncome(SourceOfIncome.ORDER_CONTRACT);
        creditCalculatorDtoTest.setContinuityOfEmployment(false);
        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertEquals(0, result);
    }

    @Test
    void shouldReturnZeroWhenSourceOfIncomeEqualsAnnuitiesAndContinuityOfEmploymentEqualsFalse() {
        CreditCalculatorDto creditCalculatorDtoTest = getCreditCalculatorDto();
        creditCalculatorDtoTest.setSourceOfIncome(SourceOfIncome.ANNUITIES);
        creditCalculatorDtoTest.setContinuityOfEmployment(false);
        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertEquals(0, result);
    }

    @Test
    void shouldReturnZeroWhenTotalMonthlyDebtsIsGreaterThanMonthlyNetIncome() {
        CreditCalculatorDto creditCalculatorDtoTest = getCreditCalculatorDto();
        creditCalculatorDtoTest.setCreditCardLimit(20000);
        creditCalculatorDtoTest.setMonthlyExpenditures(10000);
        creditCalculatorDtoTest.setMonthlyAmountOtherLoans(3000); // Total cost 13400
        creditCalculatorDtoTest.setMonthlyNetIncome(10000);
        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertEquals(0, result);
    }

    @Test
    void shouldReturnZeroWhenTotalMonthlyDebtsIsGreaterThanMonthlyNetIncomeAndNumberOfDependentsEqualsSix() {
        CreditCalculatorDto creditCalculatorDtoTest = getCreditCalculatorDto();
        creditCalculatorDtoTest.setCreditCardLimit(20000);
        creditCalculatorDtoTest.setMonthlyExpenditures(5000);
        creditCalculatorDtoTest.setNumberOfDependents(6);
        creditCalculatorDtoTest.setMonthlyAmountOtherLoans(3000); // Total cost 6900
        creditCalculatorDtoTest.setMonthlyNetIncome(10000); // Income reduced by Max_Dti 6000
        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertEquals(0, result);
    }

    @Test
    void shouldReturnZeroWhenTotalMonthlyDebtsIsGreaterThanMonthlyNetIncomeAndNumberOfDependentsEqualsTen() {
        CreditCalculatorDto creditCalculatorDtoTest = getCreditCalculatorDto();
        creditCalculatorDtoTest.setCreditCardLimit(20000);
        creditCalculatorDtoTest.setMonthlyExpenditures(5000);
        creditCalculatorDtoTest.setNumberOfDependents(10);
        creditCalculatorDtoTest.setMonthlyAmountOtherLoans(3000); // Total cost 7150
        creditCalculatorDtoTest.setMonthlyNetIncome(10000); // Income reduced by Max_Dti 6000
        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertEquals(0, result);
    }

    @Test
    void shouldReturnCreditWorthinessWhenTotalMonthlyDebtsIsLessThanMonthlyNetIncomeAndNumberOfDependentsEqualsZero() {
        CreditCalculatorDto creditCalculatorDtoTest = getCreditCalculatorDto();
        creditCalculatorDtoTest.setCreditCardLimit(20000);
        creditCalculatorDtoTest.setMonthlyExpenditures(5000);
        creditCalculatorDtoTest.setNumberOfDependents(0);
        creditCalculatorDtoTest.setMonthlyAmountOtherLoans(3000); // Total cost 4400
        creditCalculatorDtoTest.setMonthlyNetIncome(10000); // Income reduced by Max_Dti 6000
        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertNotEquals(0, result);
    }

    @Test
    void shouldReturnCreditWorthinessWhenTotalMonthlyDebtsIsLessThanMonthlyNetIncomeAndNumberOfDependentsEqualsOne() {
        CreditCalculatorDto creditCalculatorDtoTest = getCreditCalculatorDto();
        creditCalculatorDtoTest.setCreditCardLimit(20000);
        creditCalculatorDtoTest.setMonthlyExpenditures(5000);
        creditCalculatorDtoTest.setNumberOfDependents(1);
        creditCalculatorDtoTest.setMonthlyAmountOtherLoans(3000); // Total cost 4900
        creditCalculatorDtoTest.setMonthlyNetIncome(10000); // Income reduced by Max_Dti 6000
        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertNotEquals(0, result);
    }

    @Test
    void shouldReturnCreditWorthinessWhenTotalMonthlyDebtsIsLessThanMonthlyNetIncomeAndNumberOfDependentsEqualsThree() {
        CreditCalculatorDto creditCalculatorDtoTest = getCreditCalculatorDto();
        creditCalculatorDtoTest.setCreditCardLimit(20000);
        creditCalculatorDtoTest.setMonthlyExpenditures(5000);
        creditCalculatorDtoTest.setNumberOfDependents(3);
        creditCalculatorDtoTest.setMonthlyAmountOtherLoans(3000); // Total cost 5900
        creditCalculatorDtoTest.setMonthlyNetIncome(10000); // Income reduced by Max_Dti 6000
        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertNotEquals(0, result);
    }

    private static CreditCalculatorDto getCreditCalculatorDto() {
        CreditCalculatorDto creditCalculatorDto = new CreditCalculatorDto();
        creditCalculatorDto.setPropertyValue(825000);
        creditCalculatorDto.setDownPayment(200000);
        creditCalculatorDto.setCreditAmount(625000);
        creditCalculatorDto.setRepaymentPeriod(30);
        creditCalculatorDto.setSourceOfIncome(SourceOfIncome.ORDER_CONTRACT);
        creditCalculatorDto.setContractDurationInMonth(34);
        creditCalculatorDto.setContinuityOfEmployment(true);
        creditCalculatorDto.setMonthlyNetIncome(12000);
        creditCalculatorDto.setMonthlyExpenditures(3000);
        creditCalculatorDto.setDelayInLoanRepayment(false);
        creditCalculatorDto.setNumberOfDependents(2);
        creditCalculatorDto.setMonthlyAmountOtherLoans(500);
        creditCalculatorDto.setCreditCardLimit(10000);
        creditCalculatorDto.setDateOfBirth(LocalDate.of(1985, 1, 15));
        creditCalculatorDto.setPhoneNumber("123456789");
        creditCalculatorDto.setName("Jan");
        return creditCalculatorDto;
    }

}