package com.aplication.homeFinder.creditCalculator.service;

import com.aplication.homeFinder.creditCalculator.model.CreditCalculator;
import com.aplication.homeFinder.creditCalculator.repository.CreditCalculatorRepository;
import com.aplication.homeFinder.creditCalculator.service.dto.CreditCalculatorDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditCalculatorServiceTest {
    @Mock
    private CreditCalculatorRepository creditCalculatorRepository;
    @Mock
    private Calculator calculator;
    @Mock
    private CreditCalculatorMapper creditCalculatorMapper;
    @InjectMocks
    private CreditCalculatorService creditCalculatorService;

    @Test
    void shouldCheckCreditWorthinessAndReturnMaxLoanAmount() {
        //given
        CreditCalculatorDto creditCalculatorDtoTest = getCreditCalculatorDto();

        when(calculator.maxLoanAmount(creditCalculatorDtoTest)).thenReturn(1000000);

        //when
        int result = creditCalculatorService.checkCreditWorthiness(creditCalculatorDtoTest);

        //then
        assertEquals(1000000, result);
        verify(calculator).maxLoanAmount(creditCalculatorDtoTest);
    }

    @Test
    void shouldSaveCalculationWorthiness() {
        //given
        CreditCalculator creditCalculatorTest = getCreditCalculator();
        CreditCalculatorDto creditCalculatorDtoTest = getCreditCalculatorDto();

        when(creditCalculatorRepository.save(any(CreditCalculator.class))).thenReturn(creditCalculatorTest);
        when(creditCalculatorMapper.mapper(creditCalculatorDtoTest)).thenReturn(creditCalculatorTest);

        //when
        CreditCalculator creditCalculator = creditCalculatorService.saveCalculationWorthiness(creditCalculatorDtoTest);

        //then
        assertEquals(creditCalculatorTest, creditCalculator);
        verify(creditCalculatorRepository).save(any(CreditCalculator.class));
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

    private static CreditCalculator getCreditCalculator() {
        CreditCalculator creditCalculator = new CreditCalculator();
        creditCalculator.setPropertyValue(825000);
        creditCalculator.setDownPayment(200000);
        creditCalculator.setCreditAmount(625000);
        creditCalculator.setRepaymentPeriod(30);
        creditCalculator.setSourceOfIncome(SourceOfIncome.ORDER_CONTRACT);
        creditCalculator.setContractDurationInMonth(34);
        creditCalculator.setContinuityOfEmployment(true);
        creditCalculator.setMonthlyNetIncome(12000);
        creditCalculator.setMonthlyExpenditures(3000);
        creditCalculator.setDelayInLoanRepayment(false);
        creditCalculator.setNumberOfDependents(2);
        creditCalculator.setMonthlyAmountOtherLoans(500);
        creditCalculator.setCreditCardLimit(10000);
        creditCalculator.setDateOfBirth(LocalDate.of(1985, 1, 15));
        creditCalculator.setPhoneNumber("123456789");
        creditCalculator.setName("Jan");
        return creditCalculator;
    }

}
