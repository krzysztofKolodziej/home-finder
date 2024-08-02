package com.aplication.homeFinder.offer.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Currency {
    private String currency;
    private double midRate;
}
