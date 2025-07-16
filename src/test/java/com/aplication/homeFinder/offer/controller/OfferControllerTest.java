package com.aplication.homeFinder.offer.controller;

import com.aplication.homeFinder.offer.model.Offer;
import com.aplication.homeFinder.offer.model.KindOfProperty;
import com.aplication.homeFinder.offer.model.offerdetail.OfferDetails;
import com.aplication.homeFinder.offer.model.offerdetail.*;
import com.aplication.homeFinder.offer.repository.OfferRepository;
import com.aplication.homeFinder.offer.service.FilteringSchema;
import com.aplication.homeFinder.offer.service.OfferService;
import com.aplication.homeFinder.offer.service.dto.ClientMessageDto;
import com.aplication.homeFinder.offer.service.dto.OfferDetailsDto;
import com.aplication.homeFinder.offer.service.dto.OfferDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class OfferControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private OfferService offerService;

    @Test
    @Transactional
    void shouldReturnListOffersWhenOffersExist() throws Exception {
        //given
        OfferDto offerTestDto = getOfferDtoData();

        offerService.saveOffer(offerTestDto);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/offers/pln/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @Transactional
    void shouldReturnListOffersWhenProvidedInvalidCurrency() throws Exception {
        //given
        final String invalidCurrency = "currency";
        OfferDto offerTestDto = getOfferDtoData();

        offerService.saveOffer(offerTestDto);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/offers/{currency}/all", invalidCurrency)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @Transactional
    void shouldReturnEmptyListWhenNotFoundOffers() throws Exception {
        // given
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setCity("NonExistentCity");

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/offers/pln/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("city", filteringSchema.getCity()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));
    }


    @Test
    @Transactional
    void shouldReturnOfferWithDetailsWhenOfferExists() throws Exception {
        //given
        Offer offerTest = getOfferData();

        offerRepository.save(offerTest);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/offers/" + offerTest.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(jsonPath("$.price").value(825000))
                .andExpect(jsonPath("$.title").value("Super lokalizacja/trzy pokoje/dwupoziomowe/parking"))
                .andExpect(jsonPath("$.city").value("Wroclaw"))
                .andExpect(jsonPath("$.street").value("Dluga"))
                .andExpect(jsonPath("$.numberOfRooms").value(4))
                .andExpect(jsonPath("$.floor").value(3))
                .andExpect(jsonPath("$.description").value("Sprzedam mieszkanie w centrum Wroclawia"))
                .andExpect(jsonPath("$.kindOfProperty").value("MIESZKANIE"))
                .andExpect(jsonPath("$.offerDetailsDto.rent").value(500))
                .andExpect(jsonPath("$.offerDetailsDto.ownershipForm").value("PELNA_WLASNOSC"))
                .andExpect(jsonPath("$.offerDetailsDto.finishLevel").value("DO_ZAMIESZKANIA"))
                .andExpect(jsonPath("$.offerDetailsDto.parkingPlace").value("BRAK"))
                .andExpect(jsonPath("$.offerDetailsDto.heating").value("ELEKTRYCZNE"))
                .andExpect(jsonPath("$.offerDetailsDto.contactDetails").value("555444333"))
                .andExpect(jsonPath("$.offerDetailsDto.additionalInformationDto.market").value("PIERWOTNY"))
                .andExpect(jsonPath("$.offerDetailsDto.additionalInformationDto.announcerType").value("BIURO_NIERUCHOMOSCI"))
                .andExpect(jsonPath("$.offerDetailsDto.additionalInformationDto.yearOfConstruction").value(2022))
                .andExpect(jsonPath("$.offerDetailsDto.additionalInformationDto.buildingType").value("APARTAMENTOWIEC"))
                .andExpect(jsonPath("$.offerDetailsDto.additionalInformationDto.media").value("Tv, internet"))
                .andExpect(jsonPath("$.offerDetailsDto.additionalInformationDto.equipment").value("lodowka, piekarnik, kuchenka"))
                .andReturn();
    }

    @Test
    @Transactional
    void shouldReturnBadRequestWhenOfferIdIsInvalid() throws Exception {
        //given
        String badRequest = "bad request";

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/offers/" + badRequest))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Transactional
    void shouldReturnNotFoundWhenOfferDoesNotExist() throws Exception {
        //given
        long notExistentId = 9999999999L;

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/offers/" + notExistentId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"NOT_FOUND\",\"message\":\"404 NOT_FOUND \\\"Offer not found\\\"\"}"));
    }

    @Test
    @Transactional
    void shouldSaveOfferDtoAndReturnOfferDto() throws Exception {
        //given
        OfferDto offerTestDto = getOfferDtoData();

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerTestDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(jsonPath("$.price").value(825000))
                .andExpect(jsonPath("$.title").value("Super lokalizacja/trzy pokoje/dwupoziomowe/parking"))
                .andExpect(jsonPath("$.city").value("Wroclaw"))
                .andExpect(jsonPath("$.street").value("Dluga"))
                .andExpect(jsonPath("$.numberOfRooms").value(4))
                .andExpect(jsonPath("$.floor").value(3))
                .andExpect(jsonPath("$.description").value("Sprzedam mieszkanie w centrum Wroclawia"))
                .andExpect(jsonPath("$.kindOfProperty").value("MIESZKANIE"))
                .andExpect(jsonPath("$.offerDetailsDto.rent").value(500))
                .andExpect(jsonPath("$.offerDetailsDto.ownershipForm").value("PELNA_WLASNOSC"))
                .andExpect(jsonPath("$.offerDetailsDto.finishLevel").value("DO_ZAMIESZKANIA"))
                .andExpect(jsonPath("$.offerDetailsDto.parkingPlace").value("BRAK"))
                .andExpect(jsonPath("$.offerDetailsDto.heating").value("ELEKTRYCZNE"))
                .andExpect(jsonPath("$.offerDetailsDto.contactDetails").value("555444333"))
                .andExpect(jsonPath("$.offerDetailsDto.additionalInformationDto.market").value("PIERWOTNY"))
                .andExpect(jsonPath("$.offerDetailsDto.additionalInformationDto.announcerType").value("BIURO_NIERUCHOMOSCI"))
                .andExpect(jsonPath("$.offerDetailsDto.additionalInformationDto.yearOfConstruction").value(2022))
                .andExpect(jsonPath("$.offerDetailsDto.additionalInformationDto.buildingType").value("APARTAMENTOWIEC"))
                .andExpect(jsonPath("$.offerDetailsDto.additionalInformationDto.media").value("Tv, internet"))
                .andExpect(jsonPath("$.offerDetailsDto.additionalInformationDto.equipment").value("lodowka, piekarnik, kuchenka"))
                .andReturn();
    }

    @Test
    @Transactional
    void shouldReturnBadRequestWhenOfferDtoIsNull() throws Exception {
        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Transactional
    void shouldUpdateOfferDtoAndReturnOfferDto() throws Exception {
        //given
        OfferDto offerDtoTest = getOfferDtoData();
        OfferDto savedOfferDto = offerService.saveOffer(offerDtoTest);
        Offer savedOffer = offerRepository.findAll().get(0);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/offers/{id}", savedOffer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(jsonPath("$.price").value(825000))
                .andExpect(jsonPath("$.title").value("Super lokalizacja/trzy pokoje/dwupoziomowe/parking"))
                .andExpect(jsonPath("$.city").value("Wroclaw"))
                .andExpect(jsonPath("$.street").value("Dluga"))
                .andExpect(jsonPath("$.numberOfRooms").value(4))
                .andExpect(jsonPath("$.floor").value(3))
                .andExpect(jsonPath("$.description").value("Sprzedam mieszkanie w centrum Wroclawia"))
                .andExpect(jsonPath("$.kindOfProperty").value("MIESZKANIE"))
                .andExpect(jsonPath("$.offerDetailsDto.rent").value(500))
                .andExpect(jsonPath("$.offerDetailsDto.ownershipForm").value("PELNA_WLASNOSC"))
                .andExpect(jsonPath("$.offerDetailsDto.finishLevel").value("DO_ZAMIESZKANIA"))
                .andExpect(jsonPath("$.offerDetailsDto.parkingPlace").value("BRAK"))
                .andExpect(jsonPath("$.offerDetailsDto.heating").value("ELEKTRYCZNE"))
                .andExpect(jsonPath("$.offerDetailsDto.contactDetails").value("555444333"))
                .andExpect(jsonPath("$.offerDetailsDto.additionalInformationDto.market").value("PIERWOTNY"))
                .andExpect(jsonPath("$.offerDetailsDto.additionalInformationDto.announcerType").value("BIURO_NIERUCHOMOSCI"))
                .andExpect(jsonPath("$.offerDetailsDto.additionalInformationDto.yearOfConstruction").value(2022))
                .andExpect(jsonPath("$.offerDetailsDto.additionalInformationDto.buildingType").value("APARTAMENTOWIEC"))
                .andExpect(jsonPath("$.offerDetailsDto.additionalInformationDto.media").value("Tv, internet"))
                .andExpect(jsonPath("$.offerDetailsDto.additionalInformationDto.equipment").value("lodowka, piekarnik, kuchenka"))
                .andReturn();
    }

    @Test
    @Transactional
    void shouldReturnNotFoundWhenUpdateOfferDoesNotExist() throws Exception {
        //given
        OfferDto offerDtoTest = getOfferDtoData();

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/offers/{id}", 10000L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"NOT_FOUND\",\"message\":\"404 NOT_FOUND \\\"Offer not found\\\"\"}"));
    }

    @Test
    @Transactional
    void shouldDeleteOfferWhenOfferExist() throws Exception {
        //given
        OfferDto offerDtoTest = getOfferDtoData();
        OfferDto savedOfferDto = offerService.saveOffer(offerDtoTest);
        Offer savedOffer = offerRepository.findAll().get(0);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.delete("/offers/{id}", savedOffer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoTest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Test
    @Transactional
    void shouldReturnNotContentWhenOfferDoesNotExist() throws Exception {
        //given
        Long notExistingId = 2232323L;

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.delete("/offers/{id}", notExistingId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Test
    @Transactional
    void shouldSaveMessageWhenOfferExist() throws Exception {
        //given
        OfferDto offerDtoTest = getOfferDtoData();
        OfferDto savedOfferDto = offerService.saveOffer(offerDtoTest);
        Offer savedOffer = offerRepository.findAll().get(0);
        ClientMessageDto clientMessageDtoTest = ClientMessageDto.builder()
                .name("Karol")
                .email("karol@gmail.com")
                .phoneNumber("444333666")
                .message("Prosze o kontakt w sprawie oferty")
                .idOffer(savedOffer.getId())
                .build();

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/offers/{id}/message", savedOffer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientMessageDtoTest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    @Transactional
    void shouldReturnStatusNotFoundWhenOfferNotExist() throws Exception {
        //given
        long notExistentId = 9999999999L;
        ClientMessageDto clientMessageDtoTest = ClientMessageDto.builder()
                .name("Karol")
                .email("karol@gmail.com")
                .phoneNumber("444333666")
                .message("Prosze o kontakt w sprawie oferty")
                .idOffer(notExistentId)
                .build();

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/offers/{id}/message", notExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientMessageDtoTest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"NOT_FOUND\",\"message\":\"404 NOT_FOUND \\\"Offer not found\\\"\"}"));
    }

    @Test
    @Transactional
    void shouldReturnBadRequestWhenMessageDtoIsInvalid() throws Exception {
        // given
        OfferDto offerDtoTest = getOfferDtoData();
        OfferDto savedOfferDto = offerService.saveOffer(offerDtoTest);
        Offer savedOffer = offerRepository.findAll().get(0);
        ClientMessageDto clientMessageDto = new ClientMessageDto();

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/offers/{id}/message", savedOffer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientMessageDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private static Offer getOfferData() {
        return Offer.builder()
                .offerDetails(getOfferDetailsData())
                .kindOfProperty(KindOfProperty.MIESZKANIE)
                .price(825000)
                .area(85.5)
                .pricePerMeter(9649.0)
                .title("Super lokalizacja/trzy pokoje/dwupoziomowe/parking")
                .city("Wroclaw")
                .street("Dluga")
                .numberOfRooms(4)
                .floor(3)
                .description("Sprzedam mieszkanie w centrum Wroclawia")
                .build();
    }

    private static OfferDetails getOfferDetailsData() {
        return OfferDetails.builder()
                .additionalInformation(getAdditionalInformationData())
                .rent(500)
                .ownershipForm(OwnershipForm.PELNA_WLASNOSC)
                .finishLevel(FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(ParkingPlace.BRAK)
                .heating(Heating.ELEKTRYCZNE)
                .contactDetails("555444333")
                .build();
    }

    private static OfferDetails.AdditionalInformation getAdditionalInformationData() {
        return OfferDetails.AdditionalInformation.builder()
                .market(Market.PIERWOTNY)
                .announcerType(AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2022)
                .buildingType(BuildingType.APARTAMENTOWIEC)
                .media("Tv, internet")
                .equipment("lodowka, piekarnik, kuchenka")
                .build();
    }

    private static OfferDto getOfferDtoData() {
       return OfferDto.builder()
                .offerDetailsDto(getOfferDetailsDtoData())
                .kindOfProperty(KindOfProperty.MIESZKANIE)
                .price(825000)
                .area(85.5)
                .pricePerMeter(9649.0)
                .title("Super lokalizacja/trzy pokoje/dwupoziomowe/parking")
                .city("Wroclaw")
                .street("Dluga")
                .numberOfRooms(4)
                .floor(3)
                .description("Sprzedam mieszkanie w centrum Wroclawia")
                .build();
    }

    private static OfferDetailsDto getOfferDetailsDtoData() {
        return OfferDetailsDto.builder()
                .additionalInformationDto(getAdditionalInformationDtoData())
                .rent(500)
                .ownershipForm(OwnershipForm.PELNA_WLASNOSC)
                .finishLevel(FinishLevel.DO_ZAMIESZKANIA)
                .parkingPlace(ParkingPlace.BRAK)
                .heating(Heating.ELEKTRYCZNE)
                .contactDetails("555444333")
                .build();
    }

    private static OfferDetailsDto.AdditionalInformation getAdditionalInformationDtoData() {
        return OfferDetailsDto.AdditionalInformation.builder()
                .market(Market.PIERWOTNY)
                .announcerType(AnnouncerType.BIURO_NIERUCHOMOSCI)
                .yearOfConstruction(2022)
                .buildingType(BuildingType.APARTAMENTOWIEC)
                .media("Tv, internet")
                .equipment("lodowka, piekarnik, kuchenka")
                .build();
    }

}