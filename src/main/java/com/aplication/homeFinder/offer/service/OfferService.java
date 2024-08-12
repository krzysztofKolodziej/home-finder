package com.aplication.homeFinder.offer.service;

import com.aplication.homeFinder.offer.model.ClientMessage;
import com.aplication.homeFinder.offer.repository.ClientMessageRepository;
import com.aplication.homeFinder.offer.repository.OfferRepository;
import com.aplication.homeFinder.offer.model.Offer;
import com.aplication.homeFinder.offer.model.OfferDetails;
import com.aplication.homeFinder.offer.service.dto.ClientMessageDto;
import com.aplication.homeFinder.offer.service.dto.OfferDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final ClientMessageRepository clientMessageRepository;
    private final ExchangeClient exchangeClient;
    private final FilteringLogic filteringLogic;
    private final Mapper mapper;

    public List<OfferDto> findOffers(FilteringSchema filteringSchema, String currency) {
        double midRate = exchangeClient.getExchangeRate(currency).getMidRate();

        return filteringLogic.filteringMethod(filteringSchema)
                .stream()
                .map(offer -> mapper.mapOfferDto(offer, midRate))
                .collect(Collectors.toList());
    }

    public OfferDto findOfferWithDetails(Long id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));
        OfferDetails offerDetails = offer.getOfferDetails();
        if (offerDetails == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer Details not found");
        }
        return mapper.mapOfferWithDetailsDto(offer);
    }

    public OfferDto saveOffer(OfferDto offerDto) {
        Offer offer = offerRepository.save(mapper.mapOffer(offerDto));
        return mapper.mapOfferWithDetailsDto(offer);
    }

    public OfferDto updateOffer(OfferDto offerDto, Long id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));
        offerRepository.save(mapper.mapOfferEdit(offer, offerDto));
        return offerDto;
    }

    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }

    public void addMessage(ClientMessageDto clientMessageDto, Long id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));
        clientMessageRepository.save(mapper.mapClientMessage(clientMessageDto, offer));
    }

}
