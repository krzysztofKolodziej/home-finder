package com.aplication.homeFinder.offer.service.dto;

import com.aplication.homeFinder.offer.model.OfferDetails;
import jakarta.validation.Valid;
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
    @Min(value = 0, message = "Value must be greater than or equal to zero")
    private double rent;
    @NotNull(message = "Value must not be null")
    private OfferDetails.OwnershipForm ownershipForm;
    @NotNull(message = "Value must not be null")
    private OfferDetails.FinishLevel finishLevel;
    @NotNull(message = "Value must not be null")
    private OfferDetails.ParkingPlace parkingPlace;
    @NotNull(message = "Value must not be null")
    private OfferDetails.Heating heating;
    @NotBlank(message = "Value must not be empty")
    private String contactDetails;
    @NotNull(message = "Additional information can not be null")
    @Valid
    private OfferDetailsDto.AdditionalInformation additionalInformationDto;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class AdditionalInformation {
        @NotNull(message = "Value must not be null")
        private OfferDetails.Market market;
        @NotNull(message = "Value must not be null")
        private OfferDetails.AnnouncerType announcerType;
        @Min(value = 0, message = "Value must be greater than or equal to zero")
        private int yearOfConstruction;
        @NotNull(message = "Value must not be null")
        private OfferDetails.BuildingType buildingType;
        @NotBlank(message = "Value must not be empty")
        @Size(max = 1000, message = "Maximum number of characters is 1000")
        private String media;
        @NotBlank(message = "Value must not be empty")
        @Size(max = 1000, message = "Maximum number of characters is 1000")
        private String equipment;
    }
}
