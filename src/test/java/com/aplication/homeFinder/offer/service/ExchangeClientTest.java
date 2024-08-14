package com.aplication.homeFinder.offer.service;

import com.aplication.homeFinder.offer.model.Currency;
import com.aplication.homeFinder.offer.service.dto.CurrencyDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangeClientTest {

    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private ExchangeClient exchangeClient;

    @Test
    void shouldReturnCurrencyClassWhenCurrencyIsPln() {
        //given
        String currency = "pln";
        String url = "https://api.nbp.pl/api/exchangerates/rates/a/pln/?format=json";
        ExchangeClient exchangeClientTest = new ExchangeClient(restTemplate, url);

        //when
        Currency exchangeRate = exchangeClientTest.getExchangeRate(currency);

        //then
        assertEquals("pln", exchangeRate.getCurrency());
        assertEquals(1, exchangeRate.getMidRate());
    }

    @Test
    void shouldReturnCurrencyClassWhenCurrencyIsUsd() {
        //given
        String currency = "usd";
        String url = "https://api.nbp.pl/api/exchangerates/rates/a/usd/?format=json";
        ExchangeClient exchangeClientTest = new ExchangeClient(restTemplate, url);

        List<CurrencyDto.Rates> ratesList = new ArrayList<>();
        ratesList.add(new CurrencyDto.Rates(4.0d));
        CurrencyDto currencyDtoTest = new CurrencyDto("usd", ratesList);

        when(restTemplate.getForObject(url, CurrencyDto.class, currency)).thenReturn(currencyDtoTest);

        //when
        Currency exchangeRate = exchangeClientTest.getExchangeRate(currency);

        //then
        assertEquals("usd", exchangeRate.getCurrency());
        assertEquals(4.0, exchangeRate.getMidRate());
    }

    @Test
    void shouldReturnCurrencyClassWhenCurrencyIsEur() {
        //given
        String currency = "eur";
        String url = "https://api.nbp.pl/api/exchangerates/rates/a/eur/?format=json";
        ExchangeClient exchangeClientTest = new ExchangeClient(restTemplate, url);

        List<CurrencyDto.Rates> ratesList = new ArrayList<>();
        ratesList.add(new CurrencyDto.Rates(4.5d));
        CurrencyDto currencyDtoTest = new CurrencyDto("eur", ratesList);

        when(restTemplate.getForObject(url, CurrencyDto.class, currency)).thenReturn(currencyDtoTest);

        //when
        Currency exchangeRate = exchangeClientTest.getExchangeRate(currency);

        //then
        assertEquals("eur", exchangeRate.getCurrency());
        assertEquals(4.5, exchangeRate.getMidRate());
    }

    @Test
    void ShouldReturnCurrencyPlnWhenThrowException() {
        //given
        String currency = "eur";
        String url = "https://api.nbp.pl/api/exchangerates/rates/a/eur/?format=json";
        ExchangeClient exchangeClientTest = new ExchangeClient(restTemplate, url);
        when(restTemplate.getForObject(anyString(), eq(CurrencyDto.class), anyString())).thenThrow(new RestClientException("Error"));

        //when
        Currency exchangeRate = exchangeClientTest.getExchangeRate(currency);

        //then
        assertEquals("pln", exchangeRate.getCurrency());
        assertEquals(1.0, exchangeRate.getMidRate());
    }

    @Test
    void ShouldReturnCurrencyPlnWhenCurrencyDtoEqualsNull() {
        //given
        String currency = "eur";
        String url = "https://api.nbp.pl/api/exchangerates/rates/a/eur/?format=json";
        ExchangeClient exchangeClientTest = new ExchangeClient(restTemplate, url);
        when(restTemplate.getForObject(anyString(), eq(CurrencyDto.class), anyString())).thenReturn(null);

        //when
        Currency exchangeRate = exchangeClientTest.getExchangeRate(currency);

        //then
        assertEquals("pln", exchangeRate.getCurrency());
        assertEquals(1.0, exchangeRate.getMidRate());
    }

}