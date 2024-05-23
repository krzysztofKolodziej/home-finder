package com.aplication.homeFinder.agent.service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstateAgentDto {

    @NotBlank
    private String name;
    @NotBlank
    private String street;
    @Min(0)
    private int houseNumber;
    @Pattern(regexp = "\\d{2}-\\d{3}")
    private int zipCode;
    @NotBlank
    private String city;
    @Email
    private String email;
    @Pattern(regexp = "^\\d{9}$", message = "Nieprawidłowy numer telefonu")
    private int phoneNumber;
}
