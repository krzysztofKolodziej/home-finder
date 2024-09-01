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
        CreditCalculatorDto creditCalculatorDtoTest = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.ORDER_CONTRACT)
                .contractDurationInMonth(34)
                .continuityOfEmployment(true)
                .monthlyNetIncome(12000)
                .monthlyExpenditures(3000)
                .delayInLoanRepayment(true)
                .numberOfDependents(2)
                .monthlyAmountOtherLoans(500)
                .creditCardLimit(10000)
                .dateOfBirth(LocalDate.of(1985, 1, 15))
                .phoneNumber("123456789")
                .name("Jan")
                .build();

        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertEquals(0, result);
    }

    @Test
    void shouldReturnCreditWorthinessWhenFixedTermContractIsGreaterThanTwelve() {
        CreditCalculatorDto creditCalculatorDtoTest = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.FIXED_TERM_CONTRACT)
                .contractDurationInMonth(13)
                .continuityOfEmployment(true)
                .monthlyNetIncome(12000)
                .monthlyExpenditures(3000)
                .delayInLoanRepayment(false)
                .numberOfDependents(2)
                .monthlyAmountOtherLoans(500)
                .creditCardLimit(10000)
                .dateOfBirth(LocalDate.of(1985, 1, 15))
                .phoneNumber("123456789")
                .name("Jan")
                .build();

        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertNotEquals(0,result);
    }

    @Test
    void shouldReturnZeroWhenFixedTermContractIsShorterOrEqualsTwelve() {
        CreditCalculatorDto creditCalculatorDtoTest = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.FIXED_TERM_CONTRACT)
                .contractDurationInMonth(12)
                .continuityOfEmployment(true)
                .monthlyNetIncome(12000)
                .monthlyExpenditures(3000)
                .delayInLoanRepayment(false)
                .numberOfDependents(2)
                .monthlyAmountOtherLoans(500)
                .creditCardLimit(10000)
                .dateOfBirth(LocalDate.of(1985, 1, 15))
                .phoneNumber("123456789")
                .name("Jan")
                .build();

        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertEquals(0, result);
    }

    @Test
    void shouldReturnZeroWhenSourceOfIncomeEqualsBusinessOperationLessThanYear() {
        CreditCalculatorDto creditCalculatorDtoTest = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.BUSINESS_OPERATION_LESS_THAN_YEAR)
                .contractDurationInMonth(34)
                .continuityOfEmployment(true)
                .monthlyNetIncome(12000)
                .monthlyExpenditures(3000)
                .delayInLoanRepayment(false)
                .numberOfDependents(2)
                .monthlyAmountOtherLoans(500)
                .creditCardLimit(10000)
                .dateOfBirth(LocalDate.of(1985, 1, 15))
                .phoneNumber("123456789")
                .name("Jan")
                .build();

        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertEquals(0, result);
    }

    @Test
    void shouldReturnZeroWhenSourceOfIncomeEqualsContractOfSpecificWorkAndContinuityOfEmploymentEqualsFalse() {
        CreditCalculatorDto creditCalculatorDtoTest = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.CONTRACT_OF_SPECIFIC_WORK)
                .contractDurationInMonth(34)
                .continuityOfEmployment(false)
                .monthlyNetIncome(12000)
                .monthlyExpenditures(3000)
                .delayInLoanRepayment(false)
                .numberOfDependents(2)
                .monthlyAmountOtherLoans(500)
                .creditCardLimit(10000)
                .dateOfBirth(LocalDate.of(1985, 1, 15))
                .phoneNumber("123456789")
                .name("Jan")
                .build();

        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertEquals(0, result);
    }

    @Test
    void shouldReturnZeroWhenSourceOfIncomeEqualsOrderContractAndContinuityOfEmploymentEqualsFalse() {
        CreditCalculatorDto creditCalculatorDtoTest = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.ORDER_CONTRACT)
                .contractDurationInMonth(34)
                .continuityOfEmployment(false)
                .monthlyNetIncome(12000)
                .monthlyExpenditures(3000)
                .delayInLoanRepayment(false)
                .numberOfDependents(2)
                .monthlyAmountOtherLoans(500)
                .creditCardLimit(10000)
                .dateOfBirth(LocalDate.of(1985, 1, 15))
                .phoneNumber("123456789")
                .name("Jan")
                .build();

        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertEquals(0, result);
    }

    @Test
    void shouldReturnZeroWhenSourceOfIncomeEqualsAnnuitiesAndContinuityOfEmploymentEqualsFalse() {
        CreditCalculatorDto creditCalculatorDtoTest = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.ANNUITIES)
                .contractDurationInMonth(34)
                .continuityOfEmployment(false)
                .monthlyNetIncome(12000)
                .monthlyExpenditures(3000)
                .delayInLoanRepayment(false)
                .numberOfDependents(2)
                .monthlyAmountOtherLoans(500)
                .creditCardLimit(10000)
                .dateOfBirth(LocalDate.of(1985, 1, 15))
                .phoneNumber("123456789")
                .name("Jan")
                .build();

        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertEquals(0, result);
    }

    @Test
    void shouldReturnZeroWhenTotalMonthlyDebtsIsGreaterThanMonthlyNetIncome() {
        CreditCalculatorDto creditCalculatorDtoTest = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.ORDER_CONTRACT)
                .contractDurationInMonth(34)
                .continuityOfEmployment(true)
                .monthlyNetIncome(10000)
                .monthlyExpenditures(10000)
                .delayInLoanRepayment(false)
                .numberOfDependents(2)
                .monthlyAmountOtherLoans(3000)
                .creditCardLimit(20000) // Total cost 13400
                .dateOfBirth(LocalDate.of(1985, 1, 15))
                .phoneNumber("123456789")
                .name("Jan")
                .build();

        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertEquals(0, result);
    }

    @Test
    void shouldReturnZeroWhenTotalMonthlyDebtsIsGreaterThanMonthlyNetIncomeAndNumberOfDependentsEqualsSix() {
        CreditCalculatorDto creditCalculatorDtoTest = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.ORDER_CONTRACT)
                .contractDurationInMonth(34)
                .continuityOfEmployment(true)
                .monthlyNetIncome(10000) // Income reduced by Max_Dti 6000
                .monthlyExpenditures(5000)
                .delayInLoanRepayment(false)
                .numberOfDependents(6)
                .monthlyAmountOtherLoans(3000)
                .creditCardLimit(20000) // Total cost 6900
                .dateOfBirth(LocalDate.of(1985, 1, 15))
                .phoneNumber("123456789")
                .name("Jan")
                .build();

        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertEquals(0, result);
    }

    @Test
    void shouldReturnZeroWhenTotalMonthlyDebtsIsGreaterThanMonthlyNetIncomeAndNumberOfDependentsEqualsTen() {
        CreditCalculatorDto creditCalculatorDtoTest = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.ORDER_CONTRACT)
                .contractDurationInMonth(34)
                .continuityOfEmployment(true)
                .monthlyNetIncome(10000) // Income reduced by Max_Dti 6000
                .monthlyExpenditures(5000)
                .delayInLoanRepayment(false)
                .numberOfDependents(10)
                .monthlyAmountOtherLoans(3000)
                .creditCardLimit(20000) // Total cost 7150
                .dateOfBirth(LocalDate.of(1985, 1, 15))
                .phoneNumber("123456789")
                .name("Jan")
                .build();

        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertEquals(0, result);
    }

    @Test
    void shouldReturnCreditWorthinessWhenTotalMonthlyDebtsIsLessThanMonthlyNetIncomeAndNumberOfDependentsEqualsZero() {
        CreditCalculatorDto creditCalculatorDtoTest = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.ORDER_CONTRACT)
                .contractDurationInMonth(34)
                .continuityOfEmployment(true)
                .monthlyNetIncome(10000) // Income reduced by Max_Dti 6000
                .monthlyExpenditures(5000)
                .delayInLoanRepayment(false)
                .numberOfDependents(0)
                .monthlyAmountOtherLoans(3000)
                .creditCardLimit(20000) // Total cost 4400
                .dateOfBirth(LocalDate.of(1985, 1, 15))
                .phoneNumber("123456789")
                .name("Jan")
                .build();

        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertNotEquals(0, result);
    }

    @Test
    void shouldReturnCreditWorthinessWhenTotalMonthlyDebtsIsLessThanMonthlyNetIncomeAndNumberOfDependentsEqualsOne() {
        CreditCalculatorDto creditCalculatorDtoTest = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.ORDER_CONTRACT)
                .contractDurationInMonth(34)
                .continuityOfEmployment(true)
                .monthlyNetIncome(10000) // Income reduced by Max_Dti 6000
                .monthlyExpenditures(5000)
                .delayInLoanRepayment(false)
                .numberOfDependents(1)
                .monthlyAmountOtherLoans(3000) // Total cost 4900
                .creditCardLimit(20000)
                .dateOfBirth(LocalDate.of(1985, 1, 15))
                .phoneNumber("123456789")
                .name("Jan")
                .build();

        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertNotEquals(0, result);
    }

    @Test
    void shouldReturnCreditWorthinessWhenTotalMonthlyDebtsIsLessThanMonthlyNetIncomeAndNumberOfDependentsEqualsThree() {
        CreditCalculatorDto creditCalculatorDtoTest = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.ORDER_CONTRACT)
                .contractDurationInMonth(34)
                .continuityOfEmployment(true)
                .monthlyNetIncome(10000) // Income reduced by Max_Dti 6000
                .monthlyExpenditures(5000)
                .delayInLoanRepayment(false)
                .numberOfDependents(3)
                .monthlyAmountOtherLoans(3000)
                .creditCardLimit(20000) // Total cost 5900
                .dateOfBirth(LocalDate.of(1985, 1, 15))
                .phoneNumber("123456789")
                .name("Jan")
                .build();

        int result = calculator.maxLoanAmount(creditCalculatorDtoTest);
        assertNotEquals(0, result);
    }

}