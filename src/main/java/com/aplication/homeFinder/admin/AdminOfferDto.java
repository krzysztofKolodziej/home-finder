package com.aplication.homeFinder.admin;

import com.aplication.homeFinder.offer.enumClass.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminOfferDto {
    @NotNull
    private KindOfProperty kindOfProperty;
    @NotBlank
    private String title;
    @NotBlank
    private String location;
    @NotNull
    @Min(0)
    private double price;
    @NotNull
    @Min(0)
    private double area;
    @NotNull
    @Min(0)
    private double pricePerMeter;
    @NotNull
    @Min(0)
    private int numberOfRooms;
    @NotNull
    private int floor;
    @NotNull
    @Min(0)
    private double rent;
    @NotNull
    private OwnershipForm ownershipForm;
    @NotBlank
    private String description;
    @NotNull
    private BuildingType buildingType;
    @NotNull
    private Heating heating;
    @NotNull
    @Min(0)
    private int yearOfConstruction;
    @NotNull
    private FinishLevel finishLevel;
    private String media;
    private String equipment;
    @NotBlank
    private String contactDetails;
}
