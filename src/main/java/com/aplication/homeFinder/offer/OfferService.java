package com.aplication.homeFinder.offer;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;

    public Page<Offer> findOffers(Pageable pageable) {
        return offerRepository.findAll(pageable);
    }

    public OfferDto findOffer(Long id) {
        Offer offer = offerRepository.findById(id).orElse(null);
        if (offer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "nie znaleziono oferty");
        }
        return mapOfferDto(offer);
    }

    public Offer saveOffer(OfferDto offerDto, Long id) {
        return offerRepository.save(mapOffer(offerDto, id));
    }

    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }
    private Offer mapOffer(OfferDto offerDto, Long id) {
        return Offer.builder()
                .id(id)
                .floor(offerDto.getFloor())
                .area(offerDto.getArea())
                .contactDetails(offerDto.getContactDetails())
                .description(offerDto.getDescription())
                .media(offerDto.getMedia())
                .price(offerDto.getPrice())
                .heating(offerDto.getHeating())
                .buildingType(offerDto.getBuildingType())
                .finishLevel(offerDto.getFinishLevel())
                .kindOfProperty(offerDto.getKindOfProperty())
                .rent(offerDto.getRent())
                .title(offerDto.getTitle())
                .pricePerMeter(offerDto.getPricePerMeter())
                .location(offerDto.getLocation())
                .numberOfRooms(offerDto.getNumberOfRooms())
                .ownershipForm(offerDto.getOwnershipForm())
                .equipment(offerDto.getEquipment())
                .yearOfConstruction(offerDto.getYearOfConstruction())
                .build();
    }

    private OfferDto mapOfferDto(Offer offer) {
        return OfferDto.builder()
                .floor(offer.getFloor())
                .area(offer.getArea())
                .contactDetails(offer.getContactDetails())
                .description(offer.getDescription())
                .media(offer.getMedia())
                .price(offer.getPrice())
                .heating(offer.getHeating())
                .buildingType(offer.getBuildingType())
                .finishLevel(offer.getFinishLevel())
                .kindOfProperty(offer.getKindOfProperty())
                .rent(offer.getRent())
                .title(offer.getTitle())
                .pricePerMeter(offer.getPricePerMeter())
                .location(offer.getLocation())
                .numberOfRooms(offer.getNumberOfRooms())
                .ownershipForm(offer.getOwnershipForm())
                .equipment(offer.getEquipment())
                .yearOfConstruction(offer.getYearOfConstruction())
                .build();

    }
}
