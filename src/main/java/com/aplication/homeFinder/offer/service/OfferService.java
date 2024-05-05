package com.aplication.homeFinder.offer.service;

import com.aplication.homeFinder.offer.OfferRepository;
import com.aplication.homeFinder.offer.model.Offer;
import com.aplication.homeFinder.offer.model.OfferDetails;
import com.aplication.homeFinder.offer.service.dto.Mapper;
import com.aplication.homeFinder.offer.service.dto.OfferDetailsDto;
import com.aplication.homeFinder.offer.service.dto.OfferDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final Mapper mapper = new Mapper();

    public List<OfferDto> findOffers() {
        List<Offer> offers = offerRepository.findAll();
        return offers.stream().map(mapper::mapOfferDto)
                .collect(Collectors.toList());
    }

    public OfferDto findOffer(Long id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "nie znaleziono oferty"));
        return mapper.mapOfferDto(offer);
    }

    public OfferDto findOfferWithDetails(Long id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "nie znaleziono oferty"));
        OfferDetails offerDetails = offer.getOfferDetails();
        if (offerDetails == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "nie znaleziono szczegolow oferty");
        }
        return mapper.mapOfferWithDetailsDto(offer, offerDetails);
    }

    public Offer saveOffer(OfferDto offerDto, OfferDetailsDto offerDetailsDto, Long id) {
        return offerRepository.save(mapper.mapOffer(offerDto, offerDetailsDto, id));
    }

    public Offer updateOffer(OfferDto offerDto, OfferDetailsDto offerDetailsDto, Long id) {
        return offerRepository.save(mapper.mapOffer(offerDto, offerDetailsDto, id));
    }

    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }
}
