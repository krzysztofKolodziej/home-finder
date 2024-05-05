package com.aplication.homeFinder.offer.service.dto;

import com.aplication.homeFinder.offer.model.enumClass.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class OfferDetailsDto {
    @Min(0)
    private double rent;
    @NotNull
    private OwnershipForm ownershipForm;
    @NotNull
    private FinishLevel finishLevel;
    @NotNull
    private ParkingPlace parkingPlace;
    @NotNull
    private Heating heating;
    @NotBlank
    private String contactDetails;
    @NotNull
    private Market market;
    @NotNull
    private AnnouncerType announcerType;
    @Min(0)
    private int yearOfConstruction;
    @NotNull
    private BuildingType buildingType;
    @NotBlank
    private String media;
    @NotBlank
    private String equipment;
}
