package com.aplication.homeFinder.admin;

import com.aplication.homeFinder.offer.model.ClientMessage;
import com.aplication.homeFinder.offer.model.Offer;
import com.aplication.homeFinder.offer.repository.ClientMessageRepository;
import com.aplication.homeFinder.offer.repository.OfferRepository;
import com.aplication.homeFinder.offer.service.dto.ClientMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ClientMessageRepository clientMessageRepository;

    public List<ClientMessageDto> findClientMessage() {
        List<ClientMessage> clientMessageList = clientMessageRepository.findAllByOrderByOffer_Id();
        return clientMessageList.stream().map(message -> ClientMessageDto.builder()
                .name(message.getName())
                .email(message.getEmail())
                .phoneNumber(message.getPhoneNumber())
                .message(message.getMessage())
                .idOffer(message.getOffer().getId())
                .build()).toList();
    }
}