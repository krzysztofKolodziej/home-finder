package com.aplication.homeFinder.offer.service.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientMessageDto {

    @NotBlank(message = "Value must not be empty")
    private String name;

    @Email(message = "Email doesn't meet the requirements")
    @NotNull(message = "Value must not be null")
    private String email;

    @Pattern(regexp = "^\\d{9}$", message = "Invalid phone number")
    @NotNull(message = "Value must not be null")
    private String phoneNumber;

    @NotBlank(message = "Value must not be empty")
    @Size(max = 1000, message = "Maximum number of characters is 1000")
    private String message;

    @NotNull(message = "Value must not be null")
    private Long idOffer;
}
