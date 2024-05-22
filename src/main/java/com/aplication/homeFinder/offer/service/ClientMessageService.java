package com.aplication.homeFinder.offer.service;

import com.aplication.homeFinder.offer.model.ClientMessage;
import com.aplication.homeFinder.offer.model.Offer;
import com.aplication.homeFinder.offer.repository.ClientMessageRepository;
import com.aplication.homeFinder.offer.repository.OfferRepository;
import com.aplication.homeFinder.offer.service.dto.ClientMessageDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientMessageService {

    private final ClientMessageRepository clientMessageRepository;
    private final Offer offer = new Offer();

    public ClientMessage addMessage(ClientMessageDto clientMessageDto) {
        return clientMessageRepository.save(mapMessage(clientMessageDto));
    }

    private ClientMessage mapMessage(ClientMessageDto clientMessageDto) {
        ClientMessage clientMessage = ClientMessage.builder()
                .name(clientMessageDto.getName())
                .email(clientMessageDto.getEmail())
                .phoneNumber(clientMessageDto.getPhoneNumber())
                .message(clientMessageDto.getMessage())
                .build();

        offer.addClientMessage(clientMessage);

        return clientMessage;
    }
}
