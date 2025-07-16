package com.aplication.homeFinder.creditcalculator.controller;

import com.aplication.homeFinder.creditcalculator.service.SourceOfIncome;
import com.aplication.homeFinder.creditcalculator.service.dto.CreditCalculatorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class CreditCalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    void shouldSaveOfferDtoAndReturnOfferDto() throws Exception {
        //given
        CreditCalculatorDto creditCalculatorDto = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.ORDER_CONTRACT)
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

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/creditCalculator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creditCalculatorDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().string("601237"));
    }

    @Test
    @Transactional
    void shouldReturnBadRequestWhenCreditCalculatorDtoIsNull() throws Exception {
        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/creditCalculator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenFieldPropertyValueIsGreaterOrEqualsThanZero() throws Exception {
        //given
        CreditCalculatorDto creditCalculatorDto = CreditCalculatorDto.builder()
                .propertyValue(-100000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.ORDER_CONTRACT)
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

        //when & then
        mockMvc.perform(post("/creditCalculator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creditCalculatorDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must be greater than or equal to zero\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldDownPaymentIsGreaterOrEqualsThanZero() throws Exception {
        //given
        CreditCalculatorDto creditCalculatorDto = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(-100000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.ORDER_CONTRACT)
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

        //when & then
        mockMvc.perform(post("/creditCalculator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creditCalculatorDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must be greater than or equal to zero\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldCreditAmountIsGreaterOrEqualsThanZero() throws Exception {
        //given
        CreditCalculatorDto creditCalculatorDto = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(-100000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.ORDER_CONTRACT)
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

        //when & then
        mockMvc.perform(post("/creditCalculator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creditCalculatorDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must be greater than or equal to zero\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldRepaymentPeriodIsGreaterOrEqualsThanZero() throws Exception {
        //given
        CreditCalculatorDto creditCalculatorDto = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(0)
                .sourceOfIncome(SourceOfIncome.ORDER_CONTRACT)
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


        //when & then
        mockMvc.perform(post("/creditCalculator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creditCalculatorDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must be greater than or equal to one\"}"));
    }

    @Test
    void  WhenSourceOfIncomeIsDifferThanEnum() throws Exception {
        //given
        String invalidJsonWithFieldSourceOfIncome = getInvalidJsonWithFieldSourceOfIncome();

        //when & then
        mockMvc.perform(post("/creditCalculator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonWithFieldSourceOfIncome))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Invalid JSON format\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldSourceOfIncomeIsNull() throws Exception {
        //given
        CreditCalculatorDto creditCalculatorDto = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(null)
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

        //when & then
        mockMvc.perform(post("/creditCalculator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creditCalculatorDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be null\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldContractDurationIsGreaterOrEqualsThanZero() throws Exception {
        //given
        CreditCalculatorDto creditCalculatorDto = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.ORDER_CONTRACT)
                .contractDurationInMonth(3)
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

        //when & then
        mockMvc.perform(post("/creditCalculator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creditCalculatorDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must be greater than or equal to thirty-three\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldNumberOfDependentsIsGreaterOrEqualsThanZero() throws Exception {
        //given
        CreditCalculatorDto creditCalculatorDto = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.ORDER_CONTRACT)
                .contractDurationInMonth(34)
                .continuityOfEmployment(true)
                .monthlyNetIncome(12000)
                .monthlyExpenditures(3000)
                .delayInLoanRepayment(false)
                .numberOfDependents(-1)
                .monthlyAmountOtherLoans(500)
                .creditCardLimit(10000)
                .dateOfBirth(LocalDate.of(1985, 1, 15))
                .phoneNumber("123456789")
                .name("Jan")
                .build();

        //when & then
        mockMvc.perform(post("/creditCalculator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creditCalculatorDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must be greater than or equal to zero\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldMonthlyAmountOtherLoansIsGreaterOrEqualsThanZero() throws Exception {
        //given
        CreditCalculatorDto creditCalculatorDto = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.ORDER_CONTRACT)
                .contractDurationInMonth(34)
                .continuityOfEmployment(true)
                .monthlyNetIncome(12000)
                .monthlyExpenditures(3000)
                .delayInLoanRepayment(false)
                .numberOfDependents(2)
                .monthlyAmountOtherLoans(-1)
                .creditCardLimit(10000)
                .dateOfBirth(LocalDate.of(1985, 1, 15))
                .phoneNumber("123456789")
                .name("Jan")
                .build();

        //when & then
        mockMvc.perform(post("/creditCalculator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creditCalculatorDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must be greater than or equal to zero\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldCreditCardLimitIsGreaterOrEqualsThanZero() throws Exception {
        //given
        CreditCalculatorDto creditCalculatorDto = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.ORDER_CONTRACT)
                .contractDurationInMonth(34)
                .continuityOfEmployment(true)
                .monthlyNetIncome(12000)
                .monthlyExpenditures(3000)
                .delayInLoanRepayment(false)
                .numberOfDependents(2)
                .monthlyAmountOtherLoans(10000)
                .creditCardLimit(-1)
                .dateOfBirth(LocalDate.of(1985, 1, 15))
                .phoneNumber("123456789")
                .name("Jan")
                .build();

        //when & then
        mockMvc.perform(post("/creditCalculator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creditCalculatorDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must be greater than or equal to zero\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenDateOfBirthIsNull() throws Exception {
        //given
        CreditCalculatorDto creditCalculatorDto = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.ORDER_CONTRACT)
                .contractDurationInMonth(34)
                .continuityOfEmployment(true)
                .monthlyNetIncome(12000)
                .monthlyExpenditures(3000)
                .delayInLoanRepayment(false)
                .numberOfDependents(2)
                .monthlyAmountOtherLoans(1000)
                .creditCardLimit(10000)
                .dateOfBirth(null)
                .phoneNumber("123456789")
                .name("Jan")
                .build();

        //when & then
        mockMvc.perform(post("/creditCalculator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creditCalculatorDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be null\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenDateOfBirthIsPast() throws Exception {
        //given
        CreditCalculatorDto creditCalculatorDto = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.ORDER_CONTRACT)
                .contractDurationInMonth(34)
                .continuityOfEmployment(true)
                .monthlyNetIncome(12000)
                .monthlyExpenditures(3000)
                .delayInLoanRepayment(false)
                .numberOfDependents(2)
                .monthlyAmountOtherLoans(1000)
                .creditCardLimit(10000)
                .dateOfBirth(LocalDate.of(2026, 11,5))
                .phoneNumber("123456789")
                .name("Jan")
                .build();

        //when & then
        mockMvc.perform(post("/creditCalculator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creditCalculatorDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Date must be past\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenPhoneNumberIsNull() throws Exception {
        //given
        CreditCalculatorDto creditCalculatorDto = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.ORDER_CONTRACT)
                .contractDurationInMonth(34)
                .continuityOfEmployment(true)
                .monthlyNetIncome(12000)
                .monthlyExpenditures(3000)
                .delayInLoanRepayment(false)
                .numberOfDependents(2)
                .monthlyAmountOtherLoans(1000)
                .creditCardLimit(10000)
                .dateOfBirth(LocalDate.of(2000, 11,5))
                .phoneNumber(null)
                .name("Jan")
                .build();

        //when & then
        mockMvc.perform(post("/creditCalculator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creditCalculatorDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be null\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenPhoneNumberIsEmpty() throws Exception {
        //given
        CreditCalculatorDto creditCalculatorDto = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.ORDER_CONTRACT)
                .contractDurationInMonth(34)
                .continuityOfEmployment(true)
                .monthlyNetIncome(12000)
                .monthlyExpenditures(3000)
                .delayInLoanRepayment(false)
                .numberOfDependents(2)
                .monthlyAmountOtherLoans(1000)
                .creditCardLimit(10000)
                .dateOfBirth(LocalDate.of(2000, 11,5))
                .phoneNumber("   ")
                .name("Jan")
                .build();

        //when & then
        mockMvc.perform(post("/creditCalculator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creditCalculatorDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Invalid phone number\"}"));
    }

    @Test
    @Transactional
    void shouldReturnBadRequestWhenPhoneNumberIsInvalid() throws Exception {
        //given
        CreditCalculatorDto creditCalculatorDto = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.ORDER_CONTRACT)
                .contractDurationInMonth(34)
                .continuityOfEmployment(true)
                .monthlyNetIncome(12000)
                .monthlyExpenditures(3000)
                .delayInLoanRepayment(false)
                .numberOfDependents(2)
                .monthlyAmountOtherLoans(1000)
                .creditCardLimit(10000)
                .dateOfBirth(LocalDate.of(2000, 11,5))
                .phoneNumber("444333abc")
                .name("Jan")
                .build();

        //when & then
        mockMvc.perform(post("/creditCalculator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creditCalculatorDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Invalid phone number\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenNameIsNull() throws Exception {
        //given
        CreditCalculatorDto creditCalculatorDto = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.ORDER_CONTRACT)
                .contractDurationInMonth(34)
                .continuityOfEmployment(true)
                .monthlyNetIncome(12000)
                .monthlyExpenditures(3000)
                .delayInLoanRepayment(false)
                .numberOfDependents(2)
                .monthlyAmountOtherLoans(1000)
                .creditCardLimit(10000)
                .dateOfBirth(LocalDate.of(2000, 11,5))
                .phoneNumber("555444333")
                .name(null)
                .build();

        //when & then
        mockMvc.perform(post("/creditCalculator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creditCalculatorDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenNameIsEmpty() throws Exception {
        //given
        CreditCalculatorDto creditCalculatorDto = CreditCalculatorDto.builder()
                .propertyValue(825000)
                .downPayment(200000)
                .creditAmount(625000)
                .repaymentPeriod(30)
                .sourceOfIncome(SourceOfIncome.ORDER_CONTRACT)
                .contractDurationInMonth(34)
                .continuityOfEmployment(true)
                .monthlyNetIncome(12000)
                .monthlyExpenditures(3000)
                .delayInLoanRepayment(false)
                .numberOfDependents(2)
                .monthlyAmountOtherLoans(1000)
                .creditCardLimit(10000)
                .dateOfBirth(LocalDate.of(2000, 11,5))
                .phoneNumber("555444333")
                .name("   ")
                .build();

        //when & then
        mockMvc.perform(post("/creditCalculator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creditCalculatorDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }

    private static String getInvalidJsonWithFieldSourceOfIncome() {
        return """
               {
                   "propertyValue": 825000,
                   "downPayment": 200000,
                   "creditAmount": 625000,
                   "repaymentPeriod": 30,
                   "sourceOfIncome": "invalid source of income",
                   "contractDurationInMonth": 34,
                   "continuityOfEmployment": true,
                   "monthlyNetIncome": 12000,
                   "monthlyExpenditures": 3000,
                   "delayInLoanRepayment": false,
                   "numberOfDependents": 2,
                   "monthlyAmountOtherLoans": 500,
                   "creditCardLimit": 10000,
                   "dateOfBirth": "1985-01-15",
                   "phoneNumber": "123456789",
                   "name": "Jan"
               }""";
    }


}