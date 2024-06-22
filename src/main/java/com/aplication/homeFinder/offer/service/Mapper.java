package com.aplication.homeFinder.offer.service;

import com.aplication.homeFinder.offer.model.ClientMessage;
import com.aplication.homeFinder.offer.model.Offer;
import com.aplication.homeFinder.offer.model.OfferDetails;
import com.aplication.homeFinder.offer.service.dto.ClientMessageDto;
import com.aplication.homeFinder.offer.service.dto.OfferDetailsDto;
import com.aplication.homeFinder.offer.service.dto.OfferDto;

public class Mapper {

    public Offer mapOffer(Offer offer, OfferDto offerDto) {
        offer.setKindOfProperty(offerDto.getKindOfProperty());
        offer.setPrice(offerDto.getPrice());
        offer.setTitle(offerDto.getTitle());
        offer.setCity(offerDto.getCity());
        offer.setStreet(offerDto.getStreet());
        offer.setNumberOfRooms(offerDto.getNumberOfRooms());
        offer.setArea(offerDto.getArea());
        offer.setPricePerMeter(offerDto.getPricePerMeter());
        offer.setFloor(offer.getFloor());
        offer.setDescription(offerDto.getDescription());
        offer.getOfferDetails().setOwnershipForm(offerDto.getOfferDetailsDto().getOwnershipForm());
        offer.getOfferDetails().setRent(offerDto.getOfferDetailsDto().getRent());
        offer.getOfferDetails().setFinishLevel(offerDto.getOfferDetailsDto().getFinishLevel());
        offer.getOfferDetails().setParkingPlace(offerDto.getOfferDetailsDto().getParkingPlace());
        offer.getOfferDetails().setHeating(offerDto.getOfferDetailsDto().getHeating());
        offer.getOfferDetails().setContactDetails(offerDto.getOfferDetailsDto().getContactDetails());
        offer.getOfferDetails().getAdditionalInformation().setMarket(offerDto.getOfferDetailsDto().getMarket());
        offer.getOfferDetails().getAdditionalInformation().setAnnouncerType(offerDto.getOfferDetailsDto().getAnnouncerType());
        offer.getOfferDetails().getAdditionalInformation().setYearOfConstruction(offerDto.getOfferDetailsDto().getYearOfConstruction());
        offer.getOfferDetails().getAdditionalInformation().setBuildingType(offerDto.getOfferDetailsDto().getBuildingType());
        offer.getOfferDetails().getAdditionalInformation().setMedia(offerDto.getOfferDetailsDto().getMedia());
        offer.getOfferDetails().getAdditionalInformation().setEquipment(offerDto.getOfferDetailsDto().getEquipment());
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
                .market(offerDetailsDto.getMarket())
                .announcerType(offerDetailsDto.getAnnouncerType())
                .yearOfConstruction(offerDetailsDto.getYearOfConstruction())
                .buildingType(offerDetailsDto.getBuildingType())
                .media(offerDetailsDto.getMedia())
                .equipment(offerDetailsDto.getEquipment())
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

    public OfferDto mapOfferDto(Offer offer) {
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
        if (additionalInformation == null) {
            return null;
        }
        return OfferDetailsDto.builder()
                .rent(offerDetails.getRent())
                .ownershipForm(offerDetails.getOwnershipForm())
                .finishLevel(offerDetails.getFinishLevel())
                .parkingPlace(offerDetails.getParkingPlace())
                .heating(offerDetails.getHeating())
                .contactDetails(offerDetails.getContactDetails())
                .market(additionalInformation.getMarket())
                .announcerType(additionalInformation.getAnnouncerType())
                .yearOfConstruction(additionalInformation.getYearOfConstruction())
                .buildingType(additionalInformation.getBuildingType())
                .media(additionalInformation.getMedia())
                .equipment(additionalInformation.getEquipment())
                .build();
    }
}
