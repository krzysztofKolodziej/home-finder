package com.aplication.homeFinder.offer.controller;

import com.aplication.homeFinder.offer.model.Offer;
import com.aplication.homeFinder.offer.model.OfferDetails;
import com.aplication.homeFinder.offer.service.OfferService;
import com.aplication.homeFinder.offer.service.dto.ClientMessageDto;
import com.aplication.homeFinder.offer.service.dto.OfferDetailsDto;
import com.aplication.homeFinder.offer.service.dto.OfferDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
public class OfferControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OfferService offerService;

    @Test
    void shouldReturnBadRequestWhenFieldKindOfPropertyIsDifferThanEnum() throws Exception {
        //given
        String invalidJsonWithFieldKindOfProperty = getInvalidJsonWithFieldKindOfProperty();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonWithFieldKindOfProperty))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Invalid JSON format\"}"));

    }

    @Test
    void shouldReturnBadRequestWhenFieldKindOfPropertyIsNull() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(null)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be null\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldPriceIsGreaterOrEqualsThanZero() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(-100000)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must be greater than or equal to zero\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldTitleIsNull() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title(null)
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldTitleIsEmpty() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("    ")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();


        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }


    @Test
    void shouldReturnBadRequestWhenFieldCityIsNull() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city(null)
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldCityIsEmpty() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("   ")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }


    @Test
    void shouldReturnBadRequestWhenFieldStreetIsNull() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street(null)
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldStreetIsEmpty() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("   ")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();
        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldNumberOfRoomsIsLessOrEqualsThanZero() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(-1)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();
        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must be greater than or equal to zero\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldAreaIsLessOrEqualsThanZero() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(-100d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must be greater than or equal to zero\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldPricePerMeterIsLessOrEqualsThanZero() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(-10000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must be greater than or equal to zero\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldFloorIsLessOrEqualsThanZero() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(-4)
                .description("Sprzedam mieszkanie")
                .build();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must be greater than or equal to zero\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldDescriptionIsNull() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description(null)
                .build();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldDescriptionIsEmpty() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("   ")
                .build();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldDescriptionWithInvalidSize() throws Exception {
        //given
        OfferDto offerDtoTest = setDescriptionsWithInvalidSize();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Maximum number of characters is 5000\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldOfferDetailsIsNull() throws Exception {
        //given
        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(null)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Offer Details can not be null\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldRentIsLessOrEqualsThanZero() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(-1000)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();
        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must be greater than or equal to zero\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldOwnerShipFormIsDifferThanEnum() throws Exception {
        //given
        String invalidJsonWithFieldOwnerShipForm = getInvalidJsonWithFieldOwnerShipForm();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonWithFieldOwnerShipForm))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Invalid JSON format\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldOwnerShipFormIsNull() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(null)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();
        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be null\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldFinishLevelIsDifferThanEnum() throws Exception {
        //given
        String invalidJsonWithFieldFinishLevel = getInvalidJsonWithFieldFinishLevel();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonWithFieldFinishLevel))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Invalid JSON format\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFinishLevelIsNull() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(null)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be null\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldParkingPlaceIsDifferThanEnum() throws Exception {
        //given
        String invalidJsonWithFieldParkingPlace = getInvalidJsonWithFieldParkingPlace();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonWithFieldParkingPlace))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Invalid JSON format\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenParkingPlaceIsNull() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(null)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();
        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be null\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldHeatingIsDifferThanEnum() throws Exception {
        //given
        String invalidJsonWithFieldHeating = getInvalidJsonWithFieldHeating();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonWithFieldHeating))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Invalid JSON format\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenHeatingIsNull() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(null)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be null\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldContactDetailsIsNull() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails(null)
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();
        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldContactIsEmpty() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("    ")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();
        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldAdditionalInformationIsNull() throws Exception {
        //given
        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(null)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Additional information can not be null\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldMarketIsNull() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(null)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();
        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be null\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldMarketIsDifferThanEnum() throws Exception {
        //given
        String invalidJsonWithFieldMarket = getInvalidJsonWithFieldMarket();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonWithFieldMarket))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Invalid JSON format\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenAnnouncerTypeIsNull() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(null)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be null\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldAnnouncerTypeIsDifferThanEnum() throws Exception {
        //given
        String invalidJsonWithFieldAnnouncerType = getInvalidJsonWithFieldAnnouncerType();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonWithFieldAnnouncerType))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Invalid JSON format\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldYearOfConstructionIsLessOrEqualsThanZero() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(-200)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must be greater than or equal to zero\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenBuildingTypeIsNull() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(null)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();
        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be null\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldBuildingTypeIsDifferThanEnum() throws Exception {
        //given
        String invalidJsonWithFieldBuildingType = getInvalidJsonWithFieldBuildingType();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonWithFieldBuildingType))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Invalid JSON format\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldMediaIsNull() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media(null)
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldMediaIsEmpty() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("    ")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldMediaWithInvalidSize() throws Exception {
        //given
        OfferDto offerDtoTest = setMediaWithInvalidSize();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Maximum number of characters is 1000\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldEquipmentIsNull() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment(null)
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldEquipmentIsEmpty() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("   ")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        OfferDto offerDtoTest = OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();
        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldEquipmentWithInvalidSize() throws Exception {
        //given
        OfferDto offerDtoTest = setEquipmentWithInvalidSize();

        //when & then
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Maximum number of characters is 1000\"}"));
    }

    @Test
    @Transactional
    void shouldReturnBadRequestWhenFieldNameIsNull() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = getAdditionalInformationDto();
        OfferDetailsDto offerDetailsDtoTest = getOfferDetailsDto(additionalInformationDtoTest);
        OfferDto offerDtoTest = getOfferDto(offerDetailsDtoTest);
        OfferDto offerDto = offerService.saveOffer(offerDtoTest);

        ClientMessageDto clientMessageDtoInvalidName = ClientMessageDto.builder()
                .idOffer(offerDtoTest.getId())
                .name(null)
                .email("jan.kowalski@gmail.com")
                .phoneNumber("700599333")
                .message("Jestem zaintersowany ofertą. Proszę o kontakt")
                .idOffer(1L)
                .build();

        //when & then
        mockMvc.perform(post("/offers/{id}/message", offerDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientMessageDtoInvalidName)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }

    @Test
    @Transactional
    void shouldReturnBadRequestWhenFieldNameIsEmpty() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = getAdditionalInformationDto();
        OfferDetailsDto offerDetailsDtoTest = getOfferDetailsDto(additionalInformationDtoTest);
        OfferDto offerDtoTest = getOfferDto(offerDetailsDtoTest);
        OfferDto offerDto = offerService.saveOffer(offerDtoTest);

        ClientMessageDto clientMessageDtoInvalidName = ClientMessageDto.builder()
                .idOffer(offerDtoTest.getId())
                .name("   ")
                .email("jan.kowalski@gmail.com")
                .phoneNumber("700599333")
                .message("Jestem zaintersowany ofertą. Proszę o kontakt")
                .idOffer(1L)
                .build();

        //when & then
        mockMvc.perform(post("/offers/{id}/message", offerDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientMessageDtoInvalidName)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }

    @Test
    @Transactional
    void shouldReturnBadRequestWhenEmailIsEmpty() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = getAdditionalInformationDto();
        OfferDetailsDto offerDetailsDtoTest = getOfferDetailsDto(additionalInformationDtoTest);
        OfferDto offerDtoTest = getOfferDto(offerDetailsDtoTest);
        OfferDto offerDto = offerService.saveOffer(offerDtoTest);

        ClientMessageDto clientMessageDtoInvalidEmail = ClientMessageDto.builder()
                .idOffer(offerDtoTest.getId())
                .name("Jan")
                .email("  ")
                .phoneNumber("700599333")
                .message("Jestem zaintersowany ofertą. Proszę o kontakt")
                .idOffer(1L)
                .build();

        //when & then
        mockMvc.perform(post("/offers/{id}/message", offerDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientMessageDtoInvalidEmail)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Email doesn't meet the requirements\"}"));
    }

    @Test
    @Transactional
    void shouldReturnBadRequestWhenEmailIsInvalid() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = getAdditionalInformationDto();
        OfferDetailsDto offerDetailsDtoTest = getOfferDetailsDto(additionalInformationDtoTest);
        OfferDto offerDtoTest = getOfferDto(offerDetailsDtoTest);
        OfferDto offerDto = offerService.saveOffer(offerDtoTest);

        ClientMessageDto clientMessageDtoInvalidEmail = ClientMessageDto.builder()
                .idOffer(offerDtoTest.getId())
                .name("Jan")
                .email("invalid.email")
                .phoneNumber("700599333")
                .message("Jestem zaintersowany ofertą. Proszę o kontakt")
                .idOffer(1L)
                .build();

        //when & then
        mockMvc.perform(post("/offers/{id}/message", offerDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientMessageDtoInvalidEmail)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Email doesn't meet the requirements\"}"));
    }

    @Test
    @Transactional
    void shouldReturnBadRequestWhenEmailIsNull() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = getAdditionalInformationDto();
        OfferDetailsDto offerDetailsDtoTest = getOfferDetailsDto(additionalInformationDtoTest);
        OfferDto offerDtoTest = getOfferDto(offerDetailsDtoTest);
        OfferDto offerDto = offerService.saveOffer(offerDtoTest);

        ClientMessageDto clientMessageDtoInvalidEmail = ClientMessageDto.builder()
                .idOffer(offerDtoTest.getId())
                .name("Jan")
                .email(null)
                .phoneNumber("700599333")
                .message("Jestem zaintersowany ofertą. Proszę o kontakt")
                .idOffer(1L)
                .build();

        //when & then
        mockMvc.perform(post("/offers/{id}/message", offerDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientMessageDtoInvalidEmail)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be null\"}"));
    }

    @Test
    @Transactional
    void shouldReturnBadRequestWhenPhoneNumberIsInvalid() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = getAdditionalInformationDto();
        OfferDetailsDto offerDetailsDtoTest = getOfferDetailsDto(additionalInformationDtoTest);
        OfferDto offerDtoTest = getOfferDto(offerDetailsDtoTest);
        OfferDto offerDto = offerService.saveOffer(offerDtoTest);

        ClientMessageDto clientMessageDtoInvalidPhoneNumber = ClientMessageDto.builder()
                .idOffer(offerDtoTest.getId())
                .name("Jan")
                .email("jan@gmail.com")
                .phoneNumber("12345abc")
                .message("Jestem zaintersowany ofertą. Proszę o kontakt")
                .idOffer(1L)
                .build();

        //when & then
        mockMvc.perform(post("/offers/{id}/message", offerDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientMessageDtoInvalidPhoneNumber)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Invalid phone number\"}"));
    }

    @Test
    @Transactional
    void shouldReturnBadRequestWhenPhoneNumberIsEmpty() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = getAdditionalInformationDto();
        OfferDetailsDto offerDetailsDtoTest = getOfferDetailsDto(additionalInformationDtoTest);
        OfferDto offerDtoTest = getOfferDto(offerDetailsDtoTest);
        OfferDto offerDto = offerService.saveOffer(offerDtoTest);

        ClientMessageDto clientMessageDtoInvalidPhoneNumber = ClientMessageDto.builder()
                .idOffer(offerDtoTest.getId())
                .name("Jan")
                .email("jan@gmail.com")
                .phoneNumber("   ")
                .message("Jestem zaintersowany ofertą. Proszę o kontakt")
                .idOffer(1L)
                .build();

        //when & then
        mockMvc.perform(post("/offers/{id}/message", offerDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientMessageDtoInvalidPhoneNumber)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Invalid phone number\"}"));
    }

    @Test
    @Transactional
    void shouldReturnBadRequestWhenPhoneNumberIsNull() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = getAdditionalInformationDto();
        OfferDetailsDto offerDetailsDtoTest = getOfferDetailsDto(additionalInformationDtoTest);
        OfferDto offerDtoTest = getOfferDto(offerDetailsDtoTest);
        OfferDto offerDto = offerService.saveOffer(offerDtoTest);

        ClientMessageDto clientMessageDtoInvalidPhoneNumber = ClientMessageDto.builder()
                .idOffer(offerDtoTest.getId())
                .name("Jan")
                .email("jan@gmail.com")
                .phoneNumber(null)
                .message("Jestem zaintersowany ofertą. Proszę o kontakt")
                .idOffer(1L)
                .build();

        //when & then
        mockMvc.perform(post("/offers/{id}/message", offerDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientMessageDtoInvalidPhoneNumber)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be null\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldMessageIsNull() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = getAdditionalInformationDto();
        OfferDetailsDto offerDetailsDtoTest = getOfferDetailsDto(additionalInformationDtoTest);
        OfferDto offerDtoTest = getOfferDto(offerDetailsDtoTest);
        OfferDto offerDto = offerService.saveOffer(offerDtoTest);

        ClientMessageDto clientMessageDtoInvalidMessage = ClientMessageDto.builder()
                .idOffer(offerDtoTest.getId())
                .name("Jan")
                .email("jan@gmail.com")
                .phoneNumber("555222444")
                .message(null)
                .idOffer(1L)
                .build();

        //when & then
        mockMvc.perform(post("/offers/{id}/message", offerDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientMessageDtoInvalidMessage)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldMessageIsEmpty() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = getAdditionalInformationDto();
        OfferDetailsDto offerDetailsDtoTest = getOfferDetailsDto(additionalInformationDtoTest);
        OfferDto offerDtoTest = getOfferDto(offerDetailsDtoTest);
        OfferDto offerDto = offerService.saveOffer(offerDtoTest);

        ClientMessageDto clientMessageDtoInvalidMessage = ClientMessageDto.builder()
                .idOffer(offerDtoTest.getId())
                .name("Jan")
                .email("jan@gmail.com")
                .phoneNumber("555222444")
                .message("   ")
                .idOffer(1L)
                .build();

        //when & then
        mockMvc.perform(post("/offers/{id}/message", offerDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientMessageDtoInvalidMessage)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Value must not be empty\"}"));
    }

    @Test
    void shouldReturnBadRequestWhenFieldMessageWithInvalidSize() throws Exception {
        //given
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = getAdditionalInformationDto();
        OfferDetailsDto offerDetailsDtoTest = getOfferDetailsDto(additionalInformationDtoTest);
        OfferDto offerDtoTest = getOfferDto(offerDetailsDtoTest);
        OfferDto offerDto = offerService.saveOffer(offerDtoTest);

        ClientMessageDto clientMessageDto = setMessageWithInvalidSize(offerDto);

        //when & then
        mockMvc.perform(post("/offers/{id}/message", offerDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"BAD_REQUEST\",\"message\":\"Maximum number of characters is 1000\"}"));
    }


    private static OfferDto getOfferDto(OfferDetailsDto offerDetailsDtoTest) {
        return OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();
    }

    private static OfferDetailsDto getOfferDetailsDto(OfferDetailsDto.AdditionalInformation additionalInformationDtoTest) {
        return OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();
    }

    private static OfferDetailsDto.AdditionalInformation getAdditionalInformationDto() {
        return OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();
    }

    private static OfferDto setDescriptionsWithInvalidSize() {
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("pralka, piekarnik")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        return OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieM")
                .build();
    }

    private static OfferDto setMediaWithInvalidSize() {
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie")
                .equipment("pralka")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        return OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();
    }

    private static OfferDto setEquipmentWithInvalidSize() {
        OfferDetailsDto.AdditionalInformation additionalInformationDtoTest = OfferDetailsDto.AdditionalInformation.builder()
                .market(OfferDetails.Market.PIERWOTNY)
                .announcerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2019)
                .buildingType(OfferDetails.BuildingType.APARTAMENTOWIEC)
                .media("internet")
                .equipment("MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie")
                .build();

        OfferDetailsDto offerDetailsDtoTest = OfferDetailsDto.builder()
                .additionalInformationDto(additionalInformationDtoTest)
                .ownershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC)
                .rent(450d)
                .finishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(OfferDetails.ParkingPlace.MIEJSCE_NAZIEMNE)
                .heating(OfferDetails.Heating.ELEKTRYCZNE)
                .contactDetails("900300100")
                .build();

        return OfferDto.builder()
                .offerDetailsDto(offerDetailsDtoTest)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(600000d)
                .title("Sprzedam")
                .city("Wroclaw")
                .street("Poniatowskiego")
                .numberOfRooms(4)
                .area(50d)
                .pricePerMeter(12000d)
                .floor(5)
                .description("Sprzedam mieszkanie")
                .build();
    }

    private static ClientMessageDto setMessageWithInvalidSize(OfferDto offerDtoTest) {
        return ClientMessageDto.builder()
                .idOffer(offerDtoTest.getId())
                .name("Jan")
                .email("jan.kowalski@gmail.com")
                .phoneNumber("700599333")
                .message("MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie" +
                        "MieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanieMieszkanie")
                .build();
    }

    private static String getInvalidJsonWithFieldKindOfProperty() {
        return """
                {
                  "kindOfProperty": "Invalid Enum",
                  "price": 500000,
                  "title": "Sprzedam Mieszkanie",
                  "city": "Wroclaw",
                  "street": "Dluga",
                  "numberOfRooms": 3,
                  "area": 40,
                  "pricePerMeter": 12500,
                  "floor": 6,
                  "description": "Sprzedam mieszkanie niedaleko centrum",
                  "offerDetailsDto": {
                    "rent": 600,
                    "ownershipForm": "PELNA_WLASNOSC",
                    "finishLevel": "DO_ZAMIESZKANIA",
                    "parkingPlace": "BRAK",
                    "heating": "MIEJSKIE",
                    "contactDetails": "800499200",
                    "additionalInformationDto": {
                      "market": "PIERWOTNY",
                      "announcerType": "DEWELOPER",
                      "yearOfConstruction": 2010,
                      "buildingType": "BLOK",
                      "media": "internet",
                      "equipment": "pralka, lodowka"
                    }
                  }
                }""";
    }

    private static String getInvalidJsonWithFieldMarket() {
        return """
                {
                  "kindOfProperty": "MIESZKANIE",
                  "price": 500000,
                  "title": "Sprzedam Mieszkanie",
                  "city": "Wroclaw",
                  "street": "Dluga",
                  "numberOfRooms": 3,
                  "area": 40,
                  "pricePerMeter": 12500,
                  "floor": 6,
                  "description": "Sprzedam mieszkanie niedaleko centrum",
                  "offerDetailsDto": {
                    "rent": 600,
                    "ownershipForm": "PELNA_WLASNOSC",
                    "finishLevel": "DO_ZAMIESZKANIA",
                    "parkingPlace": "BRAK",
                    "heating": "MIEJSKIE",
                    "contactDetails": "800499200",
                    "additionalInformationDto": {
                      "market": "Invalid market",
                      "announcerType": "DEWELOPER",
                      "yearOfConstruction": 2010,
                      "buildingType": "BLOK",
                      "media": "internet",
                      "equipment": "pralka, lodowka"
                    }
                  }
                }""";
    }

    private static String getInvalidJsonWithFieldOwnerShipForm() {
        return """
                {
                  "kindOfProperty": "MIESZKANIE",
                  "price": 500000,
                  "title": "Sprzedam Mieszkanie",
                  "city": "Wroclaw",
                  "street": "Dluga",
                  "numberOfRooms": 3,
                  "area": 40,
                  "pricePerMeter": 12500,
                  "floor": 6,
                  "description": "Sprzedam mieszkanie niedaleko centrum",
                  "offerDetailsDto": {
                    "rent": 600,
                    "ownershipForm": "Invalid ownerShipForm",
                    "finishLevel": "DO_ZAMIESZKANIA",
                    "parkingPlace": "BRAK",
                    "heating": "MIEJSKIE",
                    "contactDetails": "800499200",
                    "additionalInformationDto": {
                      "market": "PIERWOTNY",
                      "announcerType": "DEWELOPER",
                      "yearOfConstruction": 2010,
                      "buildingType": "BLOK",
                      "media": "internet",
                      "equipment": "pralka, lodowka"
                    }
                  }
                }""";
    }

    private static String getInvalidJsonWithFieldFinishLevel() {
        return """
                {
                  "kindOfProperty": "MIESZKANIE",
                  "price": 500000,
                  "title": "Sprzedam Mieszkanie",
                  "city": "Wroclaw",
                  "street": "Dluga",
                  "numberOfRooms": 3,
                  "area": 40,
                  "pricePerMeter": 12500,
                  "floor": 6,
                  "description": "Sprzedam mieszkanie niedaleko centrum",
                  "offerDetailsDto": {
                    "rent": 600,
                    "ownershipForm": "PELNA_WLASNOSC",
                    "finishLevel": "Invalid finish level",
                    "parkingPlace": "BRAK",
                    "heating": "MIEJSKIE",
                    "contactDetails": "800499200",
                    "additionalInformationDto": {
                      "market": "PIERWOTNY",
                      "announcerType": "DEWELOPER",
                      "yearOfConstruction": 2010,
                      "buildingType": "BLOK",
                      "media": "internet",
                      "equipment": "pralka, lodowka"
                    }
                  }
                }""";
    }

    private static String getInvalidJsonWithFieldParkingPlace() {
        return """
                {
                  "kindOfProperty": "MIESZKANIE",
                  "price": 500000,
                  "title": "Sprzedam Mieszkanie",
                  "city": "Wroclaw",
                  "street": "Dluga",
                  "numberOfRooms": 3,
                  "area": 40,
                  "pricePerMeter": 12500,
                  "floor": 6,
                  "description": "Sprzedam mieszkanie niedaleko centrum",
                  "offerDetailsDto": {
                    "rent": 600,
                    "ownershipForm": "PELNA_WLASNOSC",
                    "finishLevel": "DO_ZAMIESZKANIA",
                    "parkingPlace": "Invalid Parking place",
                    "heating": "MIEJSKIE",
                    "contactDetails": "800499200",
                    "additionalInformationDto": {
                      "market": "PIERWOTNY",
                      "announcerType": "DEWELOPER",
                      "yearOfConstruction": 2010,
                      "buildingType": "BLOK",
                      "media": "internet",
                      "equipment": "pralka, lodowka"
                    }
                  }
                }""";
    }

    private static String getInvalidJsonWithFieldHeating() {
        return """
                {
                  "kindOfProperty": "MIESZKANIE",
                  "price": 500000,
                  "title": "Sprzedam Mieszkanie",
                  "city": "Wroclaw",
                  "street": "Dluga",
                  "numberOfRooms": 3,
                  "area": 40,
                  "pricePerMeter": 12500,
                  "floor": 6,
                  "description": "Sprzedam mieszkanie niedaleko centrum",
                  "offerDetailsDto": {
                    "rent": 600,
                    "ownershipForm": "PELNA_WLASNOSC",
                    "finishLevel": "DO_ZAMIESZKANIA",
                    "parkingPlace": "BRAK",
                    "heating": "Invalid heating",
                    "contactDetails": "800499200",
                    "additionalInformationDto": {
                      "market": "PIERWOTNY",
                      "announcerType": "DEWELOPER",
                      "yearOfConstruction": 2010,
                      "buildingType": "BLOK",
                      "media": "internet",
                      "equipment": "pralka, lodowka"
                    }
                  }
                }""";
    }

    private static String getInvalidJsonWithFieldAnnouncerType() {
        return """
                {
                  "kindOfProperty": "MIESZKANIE",
                  "price": 500000,
                  "title": "Sprzedam Mieszkanie",
                  "city": "Wroclaw",
                  "street": "Dluga",
                  "numberOfRooms": 3,
                  "area": 40,
                  "pricePerMeter": 12500,
                  "floor": 6,
                  "description": "Sprzedam mieszkanie niedaleko centrum",
                  "offerDetailsDto": {
                    "rent": 600,
                    "ownershipForm": "PELNA_WLASNOSC",
                    "finishLevel": "DO_ZAMIESZKANIA",
                    "parkingPlace": "BRAK",
                    "heating": "MIEJSKIE>",
                    "contactDetails": "800499200",
                    "additionalInformationDto": {
                      "market": "PIERWOTNY",
                      "announcerType": "Invalid Announcer Type",
                      "yearOfConstruction": 2010,
                      "buildingType": "BLOK",
                      "media": "internet",
                      "equipment": "pralka, lodowka"
                    }
                  }
                }""";
    }

    private static String getInvalidJsonWithFieldBuildingType() {
        return """
                {
                  "kindOfProperty": "MIESZKANIE",
                  "price": 500000,
                  "title": "Sprzedam Mieszkanie",
                  "city": "Wroclaw",
                  "street": "Dluga",
                  "numberOfRooms": 3,
                  "area": 40,
                  "pricePerMeter": 12500,
                  "floor": 6,
                  "description": "Sprzedam mieszkanie niedaleko centrum",
                  "offerDetailsDto": {
                    "rent": 600,
                    "ownershipForm": "PELNA_WLASNOSC",
                    "finishLevel": "DO_ZAMIESZKANIA",
                    "parkingPlace": "BRAK",
                    "heating": "MIEJSKIE>",
                    "contactDetails": "800499200",
                    "additionalInformationDto": {
                      "market": "PIERWOTNY",
                      "announcerType": "DEWELOPER",
                      "yearOfConstruction": 2010,
                      "buildingType": "Invalid building Type",
                      "media": "internet",
                      "equipment": "pralka, lodowka"
                    }
                  }
                }""";
    }
}
