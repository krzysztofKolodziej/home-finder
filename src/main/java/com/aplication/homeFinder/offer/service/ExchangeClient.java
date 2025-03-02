package com.aplication.homeFinder.offer.service;

import com.aplication.homeFinder.offer.model.Currency;
import com.aplication.homeFinder.offer.service.dto.CurrencyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class ExchangeClient {

    private final RestTemplate restTemplate;
    private final String url;
    private static final String CURRENCY = "pln";

    public Currency getExchangeRate(String currency) {
        CurrencyDto currencyDto;

        if (currency.equals("pln")) {
            return Currency.builder()
                    .currency(CURRENCY)
                    .midRate(1)
                    .build();
        }
        try {
            currencyDto = restTemplate.getForObject(url, CurrencyDto.class, currency);
        } catch (RestClientException e) {
            return Currency.builder()
                    .currency(CURRENCY)
                    .midRate(1)
                    .build();
        }
        if (currencyDto == null) {
            return Currency.builder()
                    .currency(CURRENCY)
                    .midRate(1)
                    .build();
        }
        return Currency.builder()
                .currency(currencyDto.getCurrency())
                .midRate(currencyDto.getRates()
                        .stream()
                        .findFirst()
                        .orElseThrow()
                        .getMid())
                .build();
    }
}

