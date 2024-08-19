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
    private AgentMapper agentMapper;
    @Mock
    EstateAgentRepository estateAgentRepository;
    @Test
    void shouldSaveEstateAgent() {
        //given
        EstateAgent estateAgent = getEstateAgent();
        EstateAgentDto estateAgentDto = getEstateAgentDto();

        when(agentMapper.mapper(estateAgentDto)).thenReturn(estateAgent);
        when(estateAgentRepository.save(estateAgent)).thenReturn(estateAgent);

        //when
        EstateAgent estateAgentTest = estateAgentService.addAgent(estateAgentDto);

        //then
        assertEquals(estateAgent, estateAgentTest);
        verify(agentMapper).mapper(estateAgentDto);
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
        when(agentMapper.mapAgent(estateAgent)).thenReturn(estateAgentDto);

        //when
        List<EstateAgentDto> foundAgents = estateAgentService.findAgent();

        //then
        assertEquals(expectedDto, foundAgents);
        verify(estateAgentRepository).findAll();
        verify(agentMapper).mapAgent(estateAgent);
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