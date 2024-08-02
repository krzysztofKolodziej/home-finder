package com.aplication.homeFinder.agent.service;

import com.aplication.homeFinder.agent.model.EstateAgent;
import com.aplication.homeFinder.agent.respository.EstateAgentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
public class EstateAgentService {

    private final EstateAgentRepository estateAgentRepository;

    public EstateAgent addAgent(EstateAgentDto estateAgentDto) {
        return estateAgentRepository.save(mapper(estateAgentDto));
    }

    public List<EstateAgentDto> findAgent() {
        return estateAgentRepository.findAll().stream().map(estateAgent -> EstateAgentDto.builder()
                .name(estateAgent.getName())
                .street(estateAgent.getStreet())
                .houseNumber(estateAgent.getHouseNumber())
                .zipCode(estateAgent.getZipCode())
                .city(estateAgent.getCity())
                .email(estateAgent.getEmail())
                .phoneNumber(estateAgent.getPhoneNumber())
                .build()).toList();
    }

    public void deleteAgent(Long id) {
        estateAgentRepository.deleteById(id);
    }

    private EstateAgent mapper(EstateAgentDto estateAgentDto) {
        return EstateAgent.builder()
                .id(null)
                .name(estateAgentDto.getName())
                .street(estateAgentDto.getStreet())
                .houseNumber(estateAgentDto.getHouseNumber())
                .zipCode(estateAgentDto.getZipCode())
                .city(estateAgentDto.getCity())
                .email(estateAgentDto.getEmail())
                .phoneNumber(estateAgentDto.getPhoneNumber())
                .build();
    }
}
