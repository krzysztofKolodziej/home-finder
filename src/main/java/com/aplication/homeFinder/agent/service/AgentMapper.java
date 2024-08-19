package com.aplication.homeFinder.agent.service;

import com.aplication.homeFinder.agent.model.EstateAgent;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public class AgentMapper {
    EstateAgent mapper(EstateAgentDto estateAgentDto) {
        Optional.ofNullable(estateAgentDto).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agent not found"));
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

    EstateAgentDto mapAgent(EstateAgent estateAgent) {
        Optional.ofNullable(estateAgent).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agent not found"));
        return EstateAgentDto.builder()
                .name(estateAgent.getName())
                .street(estateAgent.getStreet())
                .houseNumber(estateAgent.getHouseNumber())
                .zipCode(estateAgent.getZipCode())
                .city(estateAgent.getCity())
                .email(estateAgent.getEmail())
                .phoneNumber(estateAgent.getPhoneNumber())
                .build();
    }

}
