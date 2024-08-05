package com.aplication.homeFinder.offer.service.dto;

import com.aplication.homeFinder.offer.model.Offer;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfferDto {
    private Long id;
    @NotNull
    private Offer.KindOfProperty kindOfProperty;
    @Min(0)
    private double price;
    @NotBlank
    private String title;
    @NotBlank
    private String city;
    @NotBlank
    private String street;
    @Min(0)
    private int numberOfRooms;
    @Min(0)
    private double area;
    @Min(0)
    private double pricePerMeter;
    @Min(0)
    private int floor;
    @NotBlank
    @Size(max = 5000, message = "Maksymalna ilość znaków to 5000")
    private String description;
    @NotNull
    private OfferDetailsDto offerDetailsDto;
}
