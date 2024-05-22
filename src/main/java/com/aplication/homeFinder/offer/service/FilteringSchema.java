package com.aplication.homeFinder.offer.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilteringSchema {

    private String kindOfProperty;
    private Double minPrice;
    private Double maxPrice;
    private String location;
    private Integer minNumberOfRooms;
    private Integer maxNumberOfRooms;
    private Double minArea;
    private Double maxArea;
    private Double minPricePerMeter;
    private Double maxPricePerMeter;
    private Integer minFloor;
    private Integer maxFloor;
    private String ownerShipForm;
    private String finishLevel;
    private String parkingPlace;
    private String heating;
    private String market;
    private String announcerType;
    private Integer minYearOfConstruction;
    private Integer maxYearOfConstruction;
    private String buildingType;
}

