package com.aplication.homeFinder.agent.service;

import com.aplication.homeFinder.agent.model.EstateAgent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AgentMapperTest {

    @InjectMocks
    private AgentMapper agentMapper;

    @Test
    void shouldMapEstateAgentDtoToEstateAgent() {
        //given
        EstateAgent estateAgentTest = getEstateAgent();
        EstateAgentDto estateAgentDto = getEstateAgentDto();

        //when
        EstateAgent mappedEstateAgent = agentMapper.mapper(estateAgentDto);

        //then
        assertEquals(estateAgentTest.getName(), mappedEstateAgent.getName());
        assertEquals(estateAgentTest.getStreet(), mappedEstateAgent.getStreet());
        assertEquals(estateAgentTest.getZipCode(), mappedEstateAgent.getZipCode());
        assertEquals(estateAgentTest.getCity(), mappedEstateAgent.getCity());
        assertEquals(estateAgentTest.getEmail(), mappedEstateAgent.getEmail());
        assertEquals(estateAgentTest.getPhoneNumber(), mappedEstateAgent.getPhoneNumber());
    }

    @Test
    void shouldThrowResponseStatusExceptionIfEstateAgentDtoDoesNotExist() {
        //when & then
        assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> agentMapper.mapper(null))
                .withMessage("404 NOT_FOUND \"Agent not found\"");
    }

    @Test
    void shouldMapEstateAgentToEstateAgentDto() {
        //given
        EstateAgent estateAgentTest = getEstateAgent();
        EstateAgentDto estateAgentDto = getEstateAgentDto();

        //when
        EstateAgentDto mappedEstateAgentDto = agentMapper.mapAgent(estateAgentTest);

        //then
        assertEquals(estateAgentDto.getName(), mappedEstateAgentDto.getName());
        assertEquals(estateAgentDto.getStreet(), mappedEstateAgentDto.getStreet());
        assertEquals(estateAgentDto.getHouseNumber(), mappedEstateAgentDto.getHouseNumber());
        assertEquals(estateAgentDto.getZipCode(), mappedEstateAgentDto.getZipCode());
        assertEquals(estateAgentDto.getCity(), mappedEstateAgentDto.getCity());
        assertEquals(estateAgentDto.getEmail(), mappedEstateAgentDto.getEmail());
        assertEquals(estateAgentDto.getPhoneNumber(), mappedEstateAgentDto.getPhoneNumber());
    }

    @Test
    void shouldThrowResponseStatusExceptionIfEstateAgentDoesNotExist() {
        //when & then
        assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> agentMapper.mapAgent(null))
                .withMessage("404 NOT_FOUND \"Agent not found\"");
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

    private static EstateAgent getEstateAgent() {
        EstateAgent estateAgentTest = new EstateAgent();
        estateAgentTest.setName("Jan");
        estateAgentTest.setStreet("Kazimierza Wielkiego");
        estateAgentTest.setZipCode("50-333");
        estateAgentTest.setCity("Wroclaw");
        estateAgentTest.setEmail("jan@gmail.com");
        estateAgentTest.setPhoneNumber("555333222");
        return estateAgentTest;
    }

}