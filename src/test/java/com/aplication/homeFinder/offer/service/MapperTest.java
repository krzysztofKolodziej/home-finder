package com.aplication.homeFinder.offer.service;

import com.aplication.homeFinder.offer.model.Offer;
import com.aplication.homeFinder.offer.model.OfferDetails;
import com.aplication.homeFinder.offer.service.dto.ClientMessageDto;
import com.aplication.homeFinder.offer.service.dto.OfferDetailsDto;
import com.aplication.homeFinder.offer.service.dto.OfferDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MapperTest {
    @InjectMocks
    Mapper mapper;

    @Test
    void shouldMapOfferWithDetailsToOfferWithDetailsDto() {
        //given
        OfferDetails.AdditionalInformation additionalInformationTest = new OfferDetails.AdditionalInformation();
        additionalInformationTest.setMarket(OfferDetails.Market.PIERWOTNY);
        additionalInformationTest.setAnnouncerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI);
        additionalInformationTest.setYearOfConstruction(2010);
        additionalInformationTest.setBuildingType(OfferDetails.BuildingType.APARTAMENTOWIEC);
        additionalInformationTest.setEquipment("pralka, lodowka");

        OfferDetails offerDetailsTest = new OfferDetails();
        offerDetailsTest.setRent(400D);
        offerDetailsTest.setOwnershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC);
        offerDetailsTest.setFinishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA);
        offerDetailsTest.setParkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE);
        offerDetailsTest.setHeating(OfferDetails.Heating.ELEKTRYCZNE);
        offerDetailsTest.setContactDetails("333444555");
        offerDetailsTest.setAdditionalInformation(additionalInformationTest);

        Offer offerTest = new Offer();
        offerTest.setId(1L);
        offerTest.setKindOfProperty(Offer.KindOfProperty.MIESZKANIE);
        offerTest.setPrice(500000d);
        offerTest.setTitle("Spzedam mieszaknie");
        offerTest.setCity("Wroclaw");
        offerTest.setNumberOfRooms(3);
        offerTest.setArea(50d);
        offerTest.setPricePerMeter(10000d);
        offerTest.setFloor(3);
        offerTest.setDescription("Sprzedam mieszkanie w spokojnej okolicy");
        offerTest.setOfferDetails(offerDetailsTest);

        //when
        OfferDto offerDto = mapper.mapOfferWithDetailsDto(offerTest);

        //then
        assertThat(offerDto.getId()).isEqualTo(offerTest.getId());
        assertThat(offerDto.getKindOfProperty()).isEqualTo(offerTest.getKindOfProperty());
        assertThat(offerDto.getPrice()).isEqualTo(offerTest.getPrice());
        assertThat(offerDto.getTitle()).isEqualTo(offerTest.getTitle());
        assertThat(offerDto.getCity()).isEqualTo(offerTest.getCity());
        assertThat(offerDto.getStreet()).isEqualTo(offerTest.getStreet());
        assertThat(offerDto.getNumberOfRooms()).isEqualTo(offerTest.getNumberOfRooms());
        assertThat(offerDto.getArea()).isEqualTo(offerTest.getArea());
        assertThat(offerDto.getPricePerMeter()).isEqualTo(offerTest.getPricePerMeter());
        assertThat(offerDto.getFloor()).isEqualTo(offerTest.getFloor());
        assertThat(offerDto.getDescription()).isEqualTo(offerTest.getDescription());

        OfferDetailsDto offerDetailsDto = offerDto.getOfferDetailsDto();
        assertThat(offerDetailsDto.getRent()).isEqualTo(offerDetailsTest.getRent());
        assertThat(offerDetailsDto.getOwnershipForm()).isEqualTo(offerDetailsTest.getOwnershipForm());
        assertThat(offerDetailsDto.getFinishLevel()).isEqualTo(offerDetailsTest.getFinishLevel());
        assertThat(offerDetailsDto.getParkingPlace()).isEqualTo(offerDetailsTest.getParkingPlace());
        assertThat(offerDetailsDto.getHeating()).isEqualTo(offerDetailsTest.getHeating());
        assertThat(offerDetailsDto.getContactDetails()).isEqualTo(offerDetailsTest.getContactDetails());

        OfferDetailsDto.AdditionalInformationDto additionalInformationDto = offerDetailsDto.getAdditionalInformationDto();
        assertThat(additionalInformationDto.getMarket()).isEqualTo(additionalInformationTest.getMarket());
        assertThat(additionalInformationDto.getAnnouncerType()).isEqualTo(additionalInformationTest.getAnnouncerType());
        assertThat(additionalInformationDto.getYearOfConstruction()).isEqualTo(additionalInformationTest.getYearOfConstruction());
        assertThat(additionalInformationDto.getBuildingType()).isEqualTo(additionalInformationTest.getBuildingType());
        assertThat(additionalInformationDto.getEquipment()).isEqualTo(additionalInformationTest.getEquipment());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenAdditionalInformationDoesNotExists() {
        //given
        Offer offerTest = new Offer();
        OfferDetails offerDetails = new OfferDetails();
        offerDetails.setAdditionalInformation(null);
        offerTest.setOfferDetails(offerDetails);

        //when & then
        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class,
                () -> mapper.mapOfferWithDetailsDto(offerTest));

        assertEquals(HttpStatus.NOT_FOUND, responseStatusException.getStatusCode());
        assertEquals("404 NOT_FOUND \"AdditionalInformation not found\"", responseStatusException.getMessage());

    }

    @Test
    void shouldMapClientMessageDtoToClientMessage() {
        //given
        ClientMessageDto clientMessageDtoTest = new ClientMessageDto();

        //when

        //then
    }
}