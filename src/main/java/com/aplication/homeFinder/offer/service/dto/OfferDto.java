package com.aplication.homeFinder.offer.service.dto;

import com.aplication.homeFinder.offer.model.OfferDetails;
import com.aplication.homeFinder.offer.model.enumClass.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OfferDto {
    @NotNull
    private KindOfProperty kindOfProperty;
    @Min(0)
    private double price;
    @NotBlank
    private String title;
    @NotBlank
    private String location;
    @Min(0)
    private int numberOfRooms;
    @Min(0)
    private double area;
    @Min(0)
    private double pricePerMeter;
    private int floor;
    @NotBlank
    @Size(max = 5000, message = "Maksymalna ilość znaków to 5000")
    private String description;
    @NotNull
    private OfferDetailsDto offerDetailsDto;
}
