package com.aplication.homeFinder.agent.service;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstateAgentDto {
    @NotBlank(message = "Value must not be empty")
    private String name;
    @NotBlank(message = "Value must not be empty")
    private String street;
    @Min(value = 0, message = "Value must be greater than or equal to zero")
    private int houseNumber;
    @Pattern(regexp = "\\d{2}-\\d{3}", message = "Invalid zip code")
    @NotNull(message = "Value must not be null")
    private String zipCode;
    @NotBlank(message = "Value must not be empty")
    private String city;
    @Email(message = "Email doesn't meet the requirements")
    @NotNull(message = "Value must not be null")
    private String email;
    @Pattern(regexp = "^\\d{9}$", message = "Invalid phone number")
    @NotNull(message = "Value must not be null")
    private String phoneNumber;
}
