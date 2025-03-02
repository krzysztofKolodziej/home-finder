package com.aplication.homeFinder.offer.service;

import com.aplication.homeFinder.offer.model.ClientMessage;
import com.aplication.homeFinder.offer.model.Offer;
import com.aplication.homeFinder.offer.model.offerdetail.OfferDetails;
import com.aplication.homeFinder.offer.service.dto.ClientMessageDto;
import com.aplication.homeFinder.offer.service.dto.OfferDetailsDto;
import com.aplication.homeFinder.offer.service.dto.OfferDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


public class Mapper {

    public Offer mapOfferEdit(Offer offer, OfferDto offerDto) {
        if (offer == null || offerDto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        OfferDetails offerWithDetails = offer.getOfferDetails();
        OfferDetailsDto offerDtoWithDetails = offerDto.getOfferDetailsDto();

        if (offerWithDetails == null || offerDtoWithDetails == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        OfferDetails.AdditionalInformation additionalInformation = offer.getOfferDetails().getAdditionalInformation();
        OfferDetailsDto.AdditionalInformation additionalInformationDto = offerDto.getOfferDetailsDto().getAdditionalInformationDto();

        if (additionalInformation == null || additionalInformationDto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
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

        offerWithDetails.setOwnershipForm(offerDtoWithDetails.getOwnershipForm());
        offerWithDetails.setRent(offerDtoWithDetails.getRent());
        offerWithDetails.setFinishLevel(offerDtoWithDetails.getFinishLevel());
        offerWithDetails.setParkingPlace(offerDtoWithDetails.getParkingPlace());
        offerWithDetails.setHeating(offerDtoWithDetails.getHeating());
        offerWithDetails.setContactDetails(offerDtoWithDetails.getContactDetails());

        additionalInformation.setMarket(additionalInformationDto.getMarket());
        additionalInformation.setAnnouncerType(additionalInformationDto.getAnnouncerType());
        additionalInformation.setYearOfConstruction(additionalInformationDto.getYearOfConstruction());
        additionalInformation.setBuildingType(additionalInformationDto.getBuildingType());
        additionalInformation.setMedia(additionalInformationDto.getMedia());
        additionalInformation.setEquipment(additionalInformationDto.getEquipment());
        return offer;
    }

    public Offer mapOffer(OfferDto offerDto) {
        if (offerDto == null || offerDto.getOfferDetailsDto() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return Offer.builder()
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
                .offerDetails(mapOfferDetails(offerDto.getOfferDetailsDto()))
                .build();
    }

    public OfferDto mapOfferDto(Offer offer, double midRate) {
        Optional.ofNullable(offer)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));
        return OfferDto.builder()
                .id(offer.getId())
                .kindOfProperty(offer.getKindOfProperty())
                .price(midRate == 0.0d ? offer.getPrice() : offer.getPrice() / midRate)
                .title(offer.getTitle())
                .city(offer.getCity())
                .street(offer.getStreet())
                .numberOfRooms(offer.getNumberOfRooms())
                .area(offer.getArea())
                .pricePerMeter(midRate == 0.0d ? offer.getPricePerMeter() : offer.getPricePerMeter() / midRate)
                .floor(offer.getFloor())
                .description(offer.getDescription())
                .build();
    }

    public OfferDto mapOfferWithDetailsDto(Offer offer) {
        Optional.ofNullable(offer)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));
        OfferDetails offerDetails = offer.getOfferDetails();

        Optional.ofNullable(offerDetails)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "OfferDetails not found"));
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
                .offerDetailsDto(mapOfferDetailsDto(offerDetails))
                .build();
    }

    public ClientMessage mapClientMessage(ClientMessageDto clientMessageDto, Offer offer) {
        if (clientMessageDto == null || offer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return ClientMessage.builder()
                .name(clientMessageDto.getName())
                .email(clientMessageDto.getEmail())
                .phoneNumber(clientMessageDto.getPhoneNumber())
                .message(clientMessageDto.getMessage())
                .offer(offer)
                .build();
    }

    private OfferDetails mapOfferDetails(OfferDetailsDto offerDetailsDto) {
        OfferDetailsDto.AdditionalInformation additionalInformationDto = offerDetailsDto.getAdditionalInformationDto();

        Optional.ofNullable(additionalInformationDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AdditionalInformation not found"));

        OfferDetails.AdditionalInformation additionalInformationBuild = OfferDetails.AdditionalInformation.builder()
                .market(additionalInformationDto.getMarket())
                .announcerType(additionalInformationDto.getAnnouncerType())
                .yearOfConstruction(additionalInformationDto.getYearOfConstruction())
                .buildingType(additionalInformationDto.getBuildingType())
                .media(additionalInformationDto.getMedia())
                .equipment(additionalInformationDto.getEquipment())
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

    private OfferDetailsDto mapOfferDetailsDto(OfferDetails offerDetails) {
        OfferDetails.AdditionalInformation additionalInformation = offerDetails.getAdditionalInformation();
        Optional.ofNullable(additionalInformation)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AdditionalInformation not found"));

        OfferDetailsDto.AdditionalInformation additionalInformationDto = OfferDetailsDto.AdditionalInformation.builder()
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
}
