package com.aplication.homeFinder.agent.service;

import com.aplication.homeFinder.agent.model.EstateAgent;
import com.aplication.homeFinder.agent.respository.EstateAgentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EstateAgentServiceTest {
    @InjectMocks
    private EstateAgentService estateAgentService;
    @Mock
    private AgentMapper agentMapper = new AgentMapperImpl();
    @Mock
    EstateAgentRepository estateAgentRepository;
    @Test
    void shouldSaveEstateAgent() {
        //given
        EstateAgent estateAgent = getEstateAgent();
        EstateAgentDto estateAgentDto = getEstateAgentDto();

        when(agentMapper.toEstateAgent(estateAgentDto)).thenReturn(estateAgent);
        when(estateAgentRepository.save(estateAgent)).thenReturn(estateAgent);

        //when
        EstateAgent estateAgentTest = estateAgentService.addAgent(estateAgentDto);

        //then
        assertEquals(estateAgent, estateAgentTest);
        verify(agentMapper).toEstateAgent(estateAgentDto);
        verify(estateAgentRepository).save(estateAgent);
    }

    @Test
    void shouldFindAllAgents() {
        //given
        EstateAgent estateAgent = getEstateAgent();
        EstateAgentDto estateAgentDto = getEstateAgentDto();
        List<EstateAgent> estateAgents = List.of(estateAgent);
        List<EstateAgentDto> expectedDto = List.of(estateAgentDto);

        when(estateAgentRepository.findAll()).thenReturn(estateAgents);
        when(agentMapper.toDto(estateAgent)).thenReturn(estateAgentDto);

        //when
        List<EstateAgentDto> foundAgents = estateAgentService.findAgent();

        //then
        assertEquals(expectedDto, foundAgents);
        verify(estateAgentRepository).findAll();
        verify(agentMapper).toDto(estateAgent);
    }

    private static EstateAgentDto getEstateAgentDto() {
        return EstateAgentDto.builder()
                .name("Jan")
                .street("Kazimierza Wielkiego")
                .zipCode("50-333")
                .city("Wrocław")
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