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
        EstateAgentDto estateAgentDto = getEstateAgentDto();
        estateAgentDto.setName(null);

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
        EstateAgentDto estateAgentDto = getEstateAgentDto();
        estateAgentDto.setName("    ");

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
        EstateAgentDto estateAgentDto = getEstateAgentDto();
        estateAgentDto.setStreet(null);

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
        EstateAgentDto estateAgentDto = getEstateAgentDto();
        estateAgentDto.setStreet("    ");

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
        EstateAgentDto estateAgentDto = getEstateAgentDto();
        estateAgentDto.setHouseNumber(-1000000);

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
        EstateAgentDto estateAgentDto = getEstateAgentDto();
        estateAgentDto.setZipCode(null);

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
        EstateAgentDto estateAgentDto = getEstateAgentDto();
        estateAgentDto.setZipCode("    ");

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
        EstateAgentDto estateAgentDto = getEstateAgentDto();
        estateAgentDto.setZipCode("55555");

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
        EstateAgentDto estateAgentDto = getEstateAgentDto();
        estateAgentDto.setCity(null);

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
        EstateAgentDto estateAgentDto = getEstateAgentDto();
        estateAgentDto.setCity("    ");

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
        EstateAgentDto estateAgentDto = getEstateAgentDto();
        estateAgentDto.setEmail(null);

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
        EstateAgentDto estateAgentDto = getEstateAgentDto();
        estateAgentDto.setEmail("    ");

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
        EstateAgentDto estateAgentDto = getEstateAgentDto();
        estateAgentDto.setEmail("InvalidEmail");

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
        EstateAgentDto estateAgentDto = getEstateAgentDto();
        estateAgentDto.setPhoneNumber(null);

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
        EstateAgentDto estateAgentDto = getEstateAgentDto();
        estateAgentDto.setPhoneNumber("    ");

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
        EstateAgentDto estateAgentDto = getEstateAgentDto();
        estateAgentDto.setPhoneNumber("7777");

        //when & then
        mockMvc.perform(post("/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estateAgentDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Invalid phone number\"}"));
    }

    private static EstateAgentDto getEstateAgentDto() {
        EstateAgentDto estateAgentDtoTest = new EstateAgentDto();
        estateAgentDtoTest.setName("Jan");
        estateAgentDtoTest.setStreet("Kazimierza Wielkiego");
        estateAgentDtoTest.setZipCode("50-333");
        estateAgentDtoTest.setCity("Wroclaw");
        estateAgentDtoTest.setEmail("jan@gmail.com");
        estateAgentDtoTest.setPhoneNumber("555333222");
        return estateAgentDtoTest;
    }


}