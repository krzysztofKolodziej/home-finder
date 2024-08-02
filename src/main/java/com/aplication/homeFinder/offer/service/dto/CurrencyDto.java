package com.aplication.homeFinder.offer.service.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CurrencyDto {
    private String currency;
    private List<Rates> rates;
    @Getter
    public static class Rates {
        private double mid;
    }
}
