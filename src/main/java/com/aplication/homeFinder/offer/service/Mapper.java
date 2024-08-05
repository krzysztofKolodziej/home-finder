package com.aplication.homeFinder.offer.service;

import com.aplication.homeFinder.offer.model.ClientMessage;
import com.aplication.homeFinder.offer.model.Offer;
import com.aplication.homeFinder.offer.model.OfferDetails;
import com.aplication.homeFinder.offer.service.dto.ClientMessageDto;
import com.aplication.homeFinder.offer.service.dto.OfferDetailsDto;
import com.aplication.homeFinder.offer.service.dto.OfferDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


public class Mapper {


    public Offer mapOfferEdit(Offer offer, OfferDto offerDto) {
        offer.setKindOfProperty(offerDto.getKindOfProperty());
        offer.setPrice(offerDto.getPrice());
        offer.setTitle(offerDto.getTitle());
        offer.setCity(offerDto.getCity());
        offer.setStreet(offerDto.getStreet());
        offer.setNumberOfRooms(offerDto.getNumberOfRooms());
        offer.setArea(offerDto.getArea());
        offer.setPricePerMeter(offerDto.getPricePerMeter());
        offer.setFloor(offerDto.getFloor());
        offer.setDescription(offerDto.getDescription());
        offer.getOfferDetails().setOwnershipForm(offerDto.getOfferDetailsDto().getOwnershipForm());
        offer.getOfferDetails().setRent(offerDto.getOfferDetailsDto().getRent());
        offer.getOfferDetails().setFinishLevel(offerDto.getOfferDetailsDto().getFinishLevel());
        offer.getOfferDetails().setParkingPlace(offerDto.getOfferDetailsDto().getParkingPlace());
        offer.getOfferDetails().setHeating(offerDto.getOfferDetailsDto().getHeating());
        offer.getOfferDetails().setContactDetails(offerDto.getOfferDetailsDto().getContactDetails());
        offer.getOfferDetails().getAdditionalInformation().setMarket(offerDto.getOfferDetailsDto().getAdditionalInformationDto().getMarket());
        offer.getOfferDetails().getAdditionalInformation().setAnnouncerType(offerDto.getOfferDetailsDto().getAdditionalInformationDto().getAnnouncerType());
        offer.getOfferDetails().getAdditionalInformation().setYearOfConstruction(offerDto.getOfferDetailsDto().getAdditionalInformationDto().getYearOfConstruction());
        offer.getOfferDetails().getAdditionalInformation().setBuildingType(offerDto.getOfferDetailsDto().getAdditionalInformationDto().getBuildingType());
        offer.getOfferDetails().getAdditionalInformation().setMedia(offerDto.getOfferDetailsDto().getAdditionalInformationDto().getMedia());
        offer.getOfferDetails().getAdditionalInformation().setEquipment(offerDto.getOfferDetailsDto().getAdditionalInformationDto().getEquipment());
        return offer;
    }

    public Offer mapOffer(OfferDto offerDto) {
        Offer offer = Offer.builder()
                .kindOfProperty(offerDto.getKindOfProperty())
                .price(offerDto.getPrice())
                .title(offerDto.getTitle())
                .city(offerDto.getCity())
                .street(offerDto.getStreet())
                .numberOfRooms(offerDto.getNumberOfRooms())
                .area(offerDto.getArea())
                .pricePerMeter(offerDto.getPricePerMeter())
                .floor(offerDto.getFloor())
                .description(offerDto.getDescription())
                .build();
        offer.addDetails(mapOfferDetails(offerDto.getOfferDetailsDto()));
        return offer;
    }

    private OfferDetails mapOfferDetails(OfferDetailsDto offerDetailsDto) {
        OfferDetails.AdditionalInformation additionalInformationBuild = OfferDetails.AdditionalInformation.builder()
                .market(offerDetailsDto.getAdditionalInformationDto().getMarket())
                .announcerType(offerDetailsDto.getAdditionalInformationDto().getAnnouncerType())
                .yearOfConstruction(offerDetailsDto.getAdditionalInformationDto().getYearOfConstruction())
                .buildingType(offerDetailsDto.getAdditionalInformationDto().getBuildingType())
                .media(offerDetailsDto.getAdditionalInformationDto().getMedia())
                .equipment(offerDetailsDto.getAdditionalInformationDto().getEquipment())
                .build();

        return OfferDetails.builder()
                .rent(offerDetailsDto.getRent())
                .ownershipForm(offerDetailsDto.getOwnershipForm())
                .finishLevel(offerDetailsDto.getFinishLevel())
                .parkingPlace(offerDetailsDto.getParkingPlace())
                .heating(offerDetailsDto.getHeating())
                .contactDetails(offerDetailsDto.getContactDetails())
                .additionalInformation(additionalInformationBuild)
                .build();
    }

    public OfferDto mapOfferDto(Offer offer, double midRate) {
        return OfferDto.builder()
                .id(offer.getId())
                .kindOfProperty(offer.getKindOfProperty())
                .price(offer.getPrice() / midRate)
                .title(offer.getTitle())
                .city(offer.getCity())
                .street(offer.getStreet())
                .numberOfRooms(offer.getNumberOfRooms())
                .area(offer.getArea())
                .pricePerMeter(offer.getPricePerMeter() / midRate)
                .floor(offer.getFloor())
                .description(offer.getDescription())
                .build();
    }

    public OfferDto mapOfferWithDetailsDto(Offer offer) {
        return OfferDto.builder()
                .id(offer.getId())
                .kindOfProperty(offer.getKindOfProperty())
                .price(offer.getPrice())
                .title(offer.getTitle())
                .city(offer.getCity())
                .street(offer.getStreet())
                .numberOfRooms(offer.getNumberOfRooms())
                .area(offer.getArea())
                .pricePerMeter(offer.getPricePerMeter())
                .floor(offer.getFloor())
                .description(offer.getDescription())
                .offerDetailsDto(mapOfferDetailsDto(offer.getOfferDetails()))
                .build();
    }

    private OfferDetailsDto mapOfferDetailsDto(OfferDetails offerDetails) {
        OfferDetails.AdditionalInformation additionalInformation = offerDetails.getAdditionalInformation();
        Optional.ofNullable(additionalInformation)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AdditionalInformation not found"));

        OfferDetailsDto.AdditionalInformationDto additionalInformationDto = OfferDetailsDto.AdditionalInformationDto.builder()
                .market(additionalInformation.getMarket())
                .announcerType(additionalInformation.getAnnouncerType())
                .yearOfConstruction(additionalInformation.getYearOfConstruction())
                .buildingType(additionalInformation.getBuildingType())
                .media(additionalInformation.getMedia())
                .equipment(additionalInformation.getEquipment())
                .build();

        return OfferDetailsDto.builder()
                .rent(offerDetails.getRent())
                .ownershipForm(offerDetails.getOwnershipForm())
                .finishLevel(offerDetails.getFinishLevel())
                .parkingPlace(offerDetails.getParkingPlace())
                .heating(offerDetails.getHeating())
                .contactDetails(offerDetails.getContactDetails())
                .additionalInformationDto(additionalInformationDto)
                .build();
    }

    public ClientMessage mapClientMessage(ClientMessageDto clientMessageDto, Offer offer) {
        return ClientMessage.builder()
                .name(clientMessageDto.getName())
                .email(clientMessageDto.getEmail())
                .phoneNumber(clientMessageDto.getPhoneNumber())
                .message(clientMessageDto.getMessage())
                .offer(offer)
                .build();
    }
}
