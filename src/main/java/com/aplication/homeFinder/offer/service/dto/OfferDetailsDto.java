package com.aplication.homeFinder.offer.service.dto;

import com.aplication.homeFinder.offer.model.OfferDetails;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    private AdditionalInformationDto additionalInformationDto;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class AdditionalInformationDto{
        @NotNull
        private OfferDetails.Market market;
        @NotNull
        private OfferDetails.AnnouncerType announcerType;
        @Min(0)
        private int yearOfConstruction;
        @NotNull
        private OfferDetails.BuildingType buildingType;
        @NotBlank
        @Size(max = 1000, message = "Maksymalna ilość znaków to 1000")
        private String media;
        @NotBlank
        @Size(max = 1000, message = "Maksymalna ilość znaków to 1000")
        private String equipment;
    }
}
