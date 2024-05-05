package com.aplication.homeFinder.offer.service;

import com.aplication.homeFinder.offer.OfferRepository;
import com.aplication.homeFinder.offer.model.Offer;
import com.aplication.homeFinder.offer.model.OfferDetails;
import com.aplication.homeFinder.offer.service.dto.OfferDetailsDto;
import com.aplication.homeFinder.offer.service.dto.OfferDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;

    public List<Offer> findOffers() { // ma zwracać offedto
        return offerRepository.findAll();
    }

    public OfferDto findOffer(Long id) { // mapowanie obietków bez szczegółów, zrobić dwie meotdy
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "nie znaleziono oferty"));
        return mapOfferDto(offer);
    }

    public Offer saveOffer(OfferDto offerDto, OfferDetailsDto offerDetailsDto, Long id) {
        return offerRepository.save(mapOffer(offerDto,offerDetailsDto,id));
    }

    public Offer updateOffer(OfferDto offerDto, OfferDetailsDto offerDetailsDto, Long id) { // zwrócić numer oferty id
        return offerRepository.save(mapOffer(offerDto, offerDetailsDto, id));
    }

    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }

    private OfferDetails mapOfferDetails(OfferDetailsDto offerDetailsDto) {
        OfferDetails.AdditionalInformation additionalInformation = new OfferDetails.AdditionalInformation();
        additionalInformation.setMarket(offerDetailsDto.getMarket());
        additionalInformation.setAnnouncerType(offerDetailsDto.getAnnouncerType());
        additionalInformation.setYearOfConstruction(offerDetailsDto.getYearOfConstruction());
        additionalInformation.setBuildingType(offerDetailsDto.getBuildingType());
        additionalInformation.setMedia(offerDetailsDto.getMedia());
        additionalInformation.setEquipment(offerDetailsDto.getEquipment());

        OfferDetails offerDetails = new OfferDetails();
        offerDetails.setRent(offerDetailsDto.getRent());
        offerDetails.setOwnershipForm(offerDetailsDto.getOwnershipForm());
        offerDetails.setFinishLevel(offerDetailsDto.getFinishLevel());
        offerDetails.setParkingPlace(offerDetailsDto.getParkingPlace());
        offerDetails.setHeating(offerDetailsDto.getHeating());
        offerDetails.setContactDetails(offerDetailsDto.getContactDetails());
        offerDetails.setAdditionalInformation(additionalInformation);

        return offerDetails;
    }

    private Offer mapOffer(OfferDto offerDto, OfferDetailsDto offerDetailsDto, Long id) {
        Offer offer = Offer.builder()
                .id(id)
                .kindOfProperty(offerDto.getKindOfProperty())
                .price(offerDto.getPrice())
                .title(offerDto.getTitle())
                .location(offerDto.getLocation())
                .numberOfRooms(offerDto.getNumberOfRooms())
                .area(offerDto.getArea())
                .pricePerMeter(offerDto.getPricePerMeter())
                .floor(offerDto.getFloor())
                .description(offerDto.getDescription())
                .build();
        offer.addDetails(mapOfferDetails(offerDetailsDto));
        return offer;
    }

    private OfferDto mapOfferDto(Offer offer) {
        return OfferDto.builder()
                .kindOfProperty(offer.getKindOfProperty())
                .price(offer.getPrice())
                .title(offer.getTitle())
                .location(offer.getLocation())
                .numberOfRooms(offer.getNumberOfRooms())
                .area(offer.getArea())
                .pricePerMeter(offer.getPricePerMeter())
                .floor(offer.getFloor())
                .description(offer.getDescription())
                .build();
    }
}
