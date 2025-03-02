package com.aplication.homeFinder.offer.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDto {
    private String currency;
    private List<Rates> rates;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Rates {
        private double mid;
    }
}
