package com.aplication.homeFinder.offer.service.dto;

import com.aplication.homeFinder.offer.model.offerdetail.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class OfferDetailsDto {

    @Min(value = 0, message = "Value must be greater than or equal to zero")
    private double rent;

    @NotNull(message = "Value must not be null")
    private OwnershipForm ownershipForm;

    @NotNull(message = "Value must not be null")
    private FinishLevel finishLevel;

    @NotNull(message = "Value must not be null")
    private ParkingPlace parkingPlace;

    @NotNull(message = "Value must not be null")
    private Heating heating;

    @NotBlank(message = "Value must not be empty")
    private String contactDetails;

    @NotNull(message = "Additional information can not be null")
    @Valid
    private OfferDetailsDto.AdditionalInformation additionalInformationDto;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class AdditionalInformation {

        @NotNull(message = "Value must not be null")
        private Market market;

        @NotNull(message = "Value must not be null")
        private AnnouncerType announcerType;

        @Min(value = 0, message = "Value must be greater than or equal to zero")
        private int yearOfConstruction;

        @NotNull(message = "Value must not be null")
        private BuildingType buildingType;

        @NotBlank(message = "Value must not be empty")
        @Size(max = 1000, message = "Maximum number of characters is 1000")
        private String media;

        @NotBlank(message = "Value must not be empty")
        @Size(max = 1000, message = "Maximum number of characters is 1000")
        private String equipment;
    }
}
