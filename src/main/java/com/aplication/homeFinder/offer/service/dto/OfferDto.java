package com.aplication.homeFinder.offer.service.dto;

import com.aplication.homeFinder.offer.model.Offer;

import jakarta.validation.Valid;
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
    @NotNull(message = "Value must not be null")
    private Offer.KindOfProperty kindOfProperty;
    @Min(value = 0, message = "Value must be greater than or equal to zero")
    private double price;
    @NotBlank(message = "Value must not be empty")
    private String title;
    @NotBlank(message = "Value must not be empty")
    private String city;
    @NotBlank(message = "Value must not be empty")
    private String street;
    @Min(value = 0, message = "Value must be greater than or equal to zero")
    private int numberOfRooms;
    @Min(value = 0, message = "Value must be greater than or equal to zero")
    private double area;
    @Min(value = 0, message = "Value must be greater than or equal to zero")
    private double pricePerMeter;
    @Min(value = 0, message = "Value must be greater than or equal to zero")
    private int floor;
    @NotBlank(message = "Value must not be empty")
    @Size(max = 5000, message = "Maximum number of characters is 5000")
    private String description;
    @NotNull(message = "Offer Details can not be null")
    @Valid
    private OfferDetailsDto offerDetailsDto;
}
