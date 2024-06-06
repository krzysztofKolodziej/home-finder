package com.aplication.homeFinder.offer.service;

import com.aplication.homeFinder.offer.model.ClientMessage;
import com.aplication.homeFinder.offer.model.Offer;
import com.aplication.homeFinder.offer.model.OfferDetails;
import com.aplication.homeFinder.offer.service.dto.ClientMessageDto;
import com.aplication.homeFinder.offer.service.dto.OfferDetailsDto;
import com.aplication.homeFinder.offer.service.dto.OfferDto;

public class Mapper {
    public Offer mapOffer(OfferDto offerDto, Long id, Long idDetails) {
            Offer offer = Offer.builder()
                    .id(id)
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
            offer.addDetails(mapOfferDetails(offerDto.getOfferDetailsDto(),idDetails));
            return offer;
        }

        private OfferDetails mapOfferDetails(OfferDetailsDto offerDetailsDto, Long id) {
            OfferDetails.AdditionalInformation additionalInformation = new OfferDetails.AdditionalInformation();
            additionalInformation.setMarket(offerDetailsDto.getMarket());
            additionalInformation.setAnnouncerType(offerDetailsDto.getAnnouncerType());
            additionalInformation.setYearOfConstruction(offerDetailsDto.getYearOfConstruction());
            additionalInformation.setBuildingType(offerDetailsDto.getBuildingType());
            additionalInformation.setMedia(offerDetailsDto.getMedia());
            additionalInformation.setEquipment(offerDetailsDto.getEquipment());

            OfferDetails offerDetails = new OfferDetails();
            offerDetails.setId(id);
            offerDetails.setRent(offerDetailsDto.getRent());
            offerDetails.setOwnershipForm(offerDetailsDto.getOwnershipForm());
            offerDetails.setFinishLevel(offerDetailsDto.getFinishLevel());
            offerDetails.setParkingPlace(offerDetailsDto.getParkingPlace());
            offerDetails.setHeating(offerDetailsDto.getHeating());
            offerDetails.setContactDetails(offerDetailsDto.getContactDetails());
            offerDetails.setAdditionalInformation(additionalInformation);

            return offerDetails;
        }

        public OfferDto mapOfferDto(Offer offer) {
            return OfferDto.builder()
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

        public OfferDto mapOfferWithDetailsDto(Offer offer, OfferDetails offerDetails) {
            return OfferDto.builder()
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
