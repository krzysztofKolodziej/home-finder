package com.aplication.homeFinder.config;

import com.aplication.homeFinder.agent.respository.EstateAgentRepository;
import com.aplication.homeFinder.agent.service.AgentMapperToDelete;
import com.aplication.homeFinder.agent.service.EstateAgentService;
import com.aplication.homeFinder.creditCalculator.repository.CreditCalculatorRepository;
import com.aplication.homeFinder.creditCalculator.service.Calculator;
import com.aplication.homeFinder.creditCalculator.service.CreditCalculatorMapper;
import com.aplication.homeFinder.creditCalculator.service.CreditCalculatorService;
import com.aplication.homeFinder.offer.repository.ClientMessageRepository;
import com.aplication.homeFinder.offer.repository.OfferRepository;
import com.aplication.homeFinder.offer.service.ExchangeClient;
import com.aplication.homeFinder.offer.service.FilteringLogic;
import com.aplication.homeFinder.offer.service.Mapper;
import com.aplication.homeFinder.offer.service.OfferService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
    @Bean
    public AgentMapperToDelete agentMapper() {
        return new AgentMapperToDelete();
    }

    @Bean
    public EstateAgentService estateAgentService(EstateAgentRepository estateAgentRepository, AgentMapperToDelete agentMapper) {
        return new EstateAgentService(estateAgentRepository, agentMapper);
    }

    @Bean
    public Calculator calculator() {
        return new Calculator();
    }

    @Bean
    public CreditCalculatorMapper creditCalculatorMapper() {
        return new CreditCalculatorMapper();
    }

    @Bean
    public CreditCalculatorService creditCalculatorService(Calculator calculator,
                                                           CreditCalculatorRepository creditCalculatorRepository,
                                                           CreditCalculatorMapper creditCalculatorMapper) {
        return new CreditCalculatorService(creditCalculatorRepository, calculator, creditCalculatorMapper);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ExchangeClient exchangeClient(RestTemplate restTemplate, @Value("${offer.exchangeRate}") String url) {
        return new ExchangeClient(restTemplate, url);
    }

    @Bean
    public FilteringLogic filteringLogic(EntityManager entityManager) {
        return new FilteringLogic(entityManager);
    }

    @Bean
    public Mapper mapper() {
        return new Mapper();
    }

    @Bean
    public OfferService offerService(OfferRepository offerRepository, ClientMessageRepository clientMessageRepository,
                                     ExchangeClient exchangeClient, FilteringLogic filteringLogic, Mapper mapper) {
        return new OfferService(offerRepository, clientMessageRepository, exchangeClient, filteringLogic, mapper);
    }


}
