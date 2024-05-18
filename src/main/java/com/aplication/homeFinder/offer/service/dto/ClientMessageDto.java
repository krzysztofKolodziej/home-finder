package com.aplication.homeFinder.offer.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ClientMessageDto {

    private String name;
    private String email;
    private int phoneNumber;
    private String message;
    private Long idOffer;
}
