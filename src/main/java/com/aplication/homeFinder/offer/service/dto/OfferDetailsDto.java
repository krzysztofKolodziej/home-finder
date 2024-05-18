package com.aplication.homeFinder.offer.service.dto;

import com.aplication.homeFinder.offer.model.OfferDetails;
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
    private OfferDetails.OwnershipForm ownershipForm;
    @NotNull
    private OfferDetails.FinishLevel finishLevel;
    @NotNull
    private OfferDetails.ParkingPlace parkingPlace;
    @NotNull
    private OfferDetails.Heating heating;
    @NotBlank
    private String contactDetails;
    @NotNull
    private OfferDetails.Market market;
    @NotNull
    private OfferDetails.AnnouncerType announcerType;
    @Min(0)
    private int yearOfConstruction;
    @NotNull
    private OfferDetails.BuildingType buildingType;
    @NotBlank
    private String media;
    @NotBlank
    private String equipment;
}
