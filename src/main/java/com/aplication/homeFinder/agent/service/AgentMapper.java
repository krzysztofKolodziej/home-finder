package com.aplication.homeFinder.agent.service;

import com.aplication.homeFinder.agent.model.EstateAgent;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface AgentMapper {

    public EstateAgent toEstateAgent(EstateAgentDto estateAgentDto);

    public EstateAgentDto toDto(EstateAgent estateAgent);
}
