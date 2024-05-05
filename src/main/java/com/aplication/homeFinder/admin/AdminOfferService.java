package com.aplication.homeFinder.admin;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
@Service
@AllArgsConstructor
public class AdminOfferService {

    private final AdminOfferRepository adminOfferRepository;

    public Page<AdminOffer> findOffers(Pageable pageable) {
        return adminOfferRepository.findAll(pageable);
    }

    public AdminOfferDto findOffer(Long id) {
        AdminOffer adminOffer = adminOfferRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "nie znaleziono oferty"));
        return mapOfferDto(adminOffer);
    }

    public AdminOffer saveOffer(AdminOfferDto adminOfferDto, Long id) {
        return adminOfferRepository.save(mapOffer(adminOfferDto, id));
    }

    public AdminOffer updateOffer(AdminOfferDto adminOfferDto, Long id) {
        return adminOfferRepository.save(mapOffer(adminOfferDto, id));
    }

    public void deleteOffer(Long id) {
        adminOfferRepository.deleteById(id);
    }

    private AdminOffer mapOffer(AdminOfferDto adminOfferDto, Long id) {
        return AdminOffer.builder()
                .id(id)
                .floor(adminOfferDto.getFloor())
                .area(adminOfferDto.getArea())
                .contactDetails(adminOfferDto.getContactDetails())
                .description(adminOfferDto.getDescription())
                .media(adminOfferDto.getMedia())
                .price(adminOfferDto.getPrice())
                .heating(adminOfferDto.getHeating())
                .buildingType(adminOfferDto.getBuildingType())
                .finishLevel(adminOfferDto.getFinishLevel())
                .kindOfProperty(adminOfferDto.getKindOfProperty())
                .rent(adminOfferDto.getRent())
                .title(adminOfferDto.getTitle())
                .pricePerMeter(adminOfferDto.getPricePerMeter())
                .location(adminOfferDto.getLocation())
                .numberOfRooms(adminOfferDto.getNumberOfRooms())
                .ownershipForm(adminOfferDto.getOwnershipForm())
                .equipment(adminOfferDto.getEquipment())
                .yearOfConstruction(adminOfferDto.getYearOfConstruction())
                .build();
    }

    private AdminOfferDto mapOfferDto(AdminOffer adminOffer) {
        return AdminOfferDto.builder()
                .floor(adminOffer.getFloor())
                .area(adminOffer.getArea())
                .contactDetails(adminOffer.getContactDetails())
                .description(adminOffer.getDescription())
                .media(adminOffer.getMedia())
                .price(adminOffer.getPrice())
                .heating(adminOffer.getHeating())
                .buildingType(adminOffer.getBuildingType())
                .finishLevel(adminOffer.getFinishLevel())
                .kindOfProperty(adminOffer.getKindOfProperty())
                .rent(adminOffer.getRent())
                .title(adminOffer.getTitle())
                .pricePerMeter(adminOffer.getPricePerMeter())
                .location(adminOffer.getLocation())
                .numberOfRooms(adminOffer.getNumberOfRooms())
                .ownershipForm(adminOffer.getOwnershipForm())
                .equipment(adminOffer.getEquipment())
                .yearOfConstruction(adminOffer.getYearOfConstruction())
                .build();

    }
}
