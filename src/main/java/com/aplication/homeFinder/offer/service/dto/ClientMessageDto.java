package com.aplication.homeFinder.offer.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ClientMessageDto {

    @NotBlank
    private String name;
    @Email
    private String email;
    @Pattern(regexp = "^\\d{9}$", message = "Nieprawidłowy numer telefonu")
    private String phoneNumber;
    @NotBlank
    @Size(max = 1000, message = "Maksymalna ilość znaków to 1000")
    private String message;
    private Long idOffer;
}
