package com.aplication.homeFinder.agent.controller;

import com.aplication.homeFinder.agent.service.EstateAgentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EstateAgentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    void shouldSaveEstateAgent() throws Exception {
        //given
        EstateAgentDto estateAgentDto = getEstateAgentDto();

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estateAgentDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().string("Estate agent added"));
    }

    @Test
    @Transactional
    void shouldReturnBadRequestWhenEstateAgentIsNull() throws Exception {
        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Transactional
    void shouldReturnListEstateAgents() throws Exception {
        //given
        EstateAgentDto estateAgentDto = getEstateAgentDto();

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estateAgentDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(Matchers.greaterThan(0)));
    }

    @Test
    void shouldReturnBadRequestWhenNameIsNull() throws Exception {
        //given
        EstateAgentDto estateAgentDto = EstateAgentDto.builder()
                .name(null)
                .street("Kazimierza Wielkiego")
                .zipCode("50-333")
                .city("Wrocław")
                .email("jan@gmail.com")
                .phoneNumber("555333222")
                .build();

        //when & then
        mockMvc.perform(post("/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estateAgentDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenNameIsEmpty() throws Exception {
        //given
        EstateAgentDto estateAgentDto = EstateAgentDto.builder()
                .name("   ")
                .street("Kazimierza Wielkiego")
                .zipCode("50-333")
                .city("Wrocław")
                .email("jan@gmail.com")
                .phoneNumber("555333222")
                .build();

        //when & then
        mockMvc.perform(post("/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estateAgentDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenStreetIsNull() throws Exception {
        //given
        EstateAgentDto estateAgentDto = EstateAgentDto.builder()
                .name("Jan")
                .street(null)
                .zipCode("50-333")
                .city("Wrocław")
                .email("jan@gmail.com")
                .phoneNumber("555333222")
                .build();

        //when & then
        mockMvc.perform(post("/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estateAgentDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenStreetIsEmpty() throws Exception {
        //given
        EstateAgentDto estateAgentDto = EstateAgentDto.builder()
                .name("Jan")
                .street("   ")
                .zipCode("50-333")
                .city("Wrocław")
                .email("jan@gmail.com")
                .phoneNumber("555333222")
                .build();

        //when & then
        mockMvc.perform(post("/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estateAgentDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldHouseNumberIsGreaterOrEqualsThanZero() throws Exception {
        //given
        EstateAgentDto estateAgentDto = EstateAgentDto.builder()
                .name("Jan")
                .street("Kazimierza Wielkiego")
                .zipCode("50-333")
                .houseNumber(-10000000)
                .city("Wrocław")
                .email("jan@gmail.com")
                .phoneNumber("555333222")
                .build();

        //when & then
        mockMvc.perform(post("/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estateAgentDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must be greater than or equal to zero\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenZipCodeIsNull() throws Exception {
        //given
        EstateAgentDto estateAgentDto = EstateAgentDto.builder()
                .name("Jan")
                .street("Kazimierza Wielkiego")
                .zipCode(null)
                .houseNumber(4)
                .city("Wrocław")
                .email("jan@gmail.com")
                .phoneNumber("555333222")
                .build();

        //when & then
        mockMvc.perform(post("/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estateAgentDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be null\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenZipCodeIsEmpty() throws Exception {
        //given
        EstateAgentDto estateAgentDto = EstateAgentDto.builder()
                .name("Jan")
                .street("Kazimierza Wielkiego")
                .zipCode("  ")
                .houseNumber(4)
                .city("Wrocław")
                .email("jan@gmail.com")
                .phoneNumber("555333222")
                .build();

        //when & then
        mockMvc.perform(post("/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estateAgentDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Invalid zip code\"}"));
    }

    @Test
    @Transactional
    void shouldReturnBadRequestWhenZipCodeIsInvalid() throws Exception {
        //given
        EstateAgentDto estateAgentDto = EstateAgentDto.builder()
                .name("Jan")
                .street("Kazimierza Wielkiego")
                .zipCode("33333")
                .houseNumber(4)
                .city("Wrocław")
                .email("jan@gmail.com")
                .phoneNumber("555333222")
                .build();

        //when & then
        mockMvc.perform(post("/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estateAgentDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Invalid zip code\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenCityIsNull() throws Exception {
        //given
        EstateAgentDto estateAgentDto = EstateAgentDto.builder()
                .name("Jan")
                .street("Kazimierza Wielkiego")
                .zipCode("66-222")
                .houseNumber(4)
                .city(null)
                .email("jan@gmail.com")
                .phoneNumber("555333222")
                .build();

        //when & then
        mockMvc.perform(post("/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estateAgentDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenCityIsEmpty() throws Exception {
        //given
        EstateAgentDto estateAgentDto = EstateAgentDto.builder()
                .name("Jan")
                .street("Kazimierza Wielkiego")
                .zipCode("66-222")
                .houseNumber(4)
                .city("   ")
                .email("jan@gmail.com")
                .phoneNumber("555333222")
                .build();

        //when & then
        mockMvc.perform(post("/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estateAgentDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }


    @Test
    void shouldReturnBadRequestWhenEmailIsNull() throws Exception {
        //given
        EstateAgentDto estateAgentDto = EstateAgentDto.builder()
                .name("Jan")
                .street("Kazimierza Wielkiego")
                .zipCode("66-222")
                .houseNumber(4)
                .city("Wroclaw")
                .email(null)
                .phoneNumber("555333222")
                .build();

        //when & then
        mockMvc.perform(post("/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estateAgentDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be null\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenEmailIsEmpty() throws Exception {
        //given
        EstateAgentDto estateAgentDto = EstateAgentDto.builder()
                .name("Jan")
                .street("Kazimierza Wielkiego")
                .zipCode("66-222")
                .houseNumber(4)
                .city("Wroclaw")
                .email("   ")
                .phoneNumber("555333222")
                .build();

        //when & then
        mockMvc.perform(post("/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estateAgentDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Email doesn't meet the requirements\"}"));
    }

    @Test
    @Transactional
    void shouldReturnBadRequestWhenEmailNIsInvalid() throws Exception {
        //given
        EstateAgentDto estateAgentDto = EstateAgentDto.builder()
                .name("Jan")
                .street("Kazimierza Wielkiego")
                .zipCode("66-222")
                .houseNumber(4)
                .city("Wroclaw")
                .email("InvalidEmail")
                .phoneNumber("555333222")
                .build();

        //when & then
        mockMvc.perform(post("/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estateAgentDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Email doesn't meet the requirements\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenPhoneNumberIsNull() throws Exception {
        //given
        EstateAgentDto estateAgentDto = EstateAgentDto.builder()
                .name("Jan")
                .street("Kazimierza Wielkiego")
                .zipCode("66-222")
                .houseNumber(4)
                .city("Wroclaw")
                .email("jan@gmail.com")
                .phoneNumber(null)
                .build();

        //when & then
        mockMvc.perform(post("/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estateAgentDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be null\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenPhoneNumberIsEmpty() throws Exception {
        //given
        EstateAgentDto estateAgentDto = EstateAgentDto.builder()
                .name("Jan")
                .street("Kazimierza Wielkiego")
                .zipCode("66-222")
                .houseNumber(4)
                .city("Wroclaw")
                .email("jan@gmail.com")
                .phoneNumber("   ")
                .build();

        //when & then
        mockMvc.perform(post("/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estateAgentDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Invalid phone number\"}"));
    }

    @Test
    @Transactional
    void shouldReturnBadRequestWhenPhoneNumberNIsInvalid() throws Exception {
        //given
        EstateAgentDto estateAgentDto = EstateAgentDto.builder()
                .name("Jan")
                .street("Kazimierza Wielkiego")
                .zipCode("66-222")
                .houseNumber(4)
                .city("Wroclaw")
                .email("jan@gmail.com")
                .phoneNumber("7777")
                .build();

        //when & then
        mockMvc.perform(post("/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estateAgentDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Invalid phone number\"}"));
    }

    private static EstateAgentDto getEstateAgentDto() {
        return EstateAgentDto.builder()
                .name("Jan")
                .street("Kazimierza Wielkiego")
                .zipCode("50-333")
                .city("Wroclaw")
                .email("jan@gmail.com")
                .phoneNumber("555333222")
                .build();
    }


}