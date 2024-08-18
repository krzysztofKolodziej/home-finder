package com.aplication.homeFinder.offer.controller;

import com.aplication.homeFinder.offer.model.Offer;
import com.aplication.homeFinder.offer.model.OfferDetails;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
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

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/offers/pln/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerTestDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(Matchers.greaterThan(0)));

    }

    @Test
    @Transactional
    void shouldReturnListOffersWhenProvidedInvalidCurrency() throws Exception {
        //given
        final String invalidCurrency = "currency";
        OfferDto offerTestDto = getOfferDtoData();

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/offers/{currency}/all", invalidCurrency)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerTestDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(Matchers.greaterThan(0)));

    }

    @Test
    @Transactional
    void shouldReturnEmptyListWhenNoOffersFound() throws Exception {
        // given
        OfferDto offerTestDto = getOfferDtoData();
        FilteringSchema filteringSchema = new FilteringSchema();
        filteringSchema.setCity("NonExistentCity");

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/offers/pln/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerTestDto))
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
    void shouldReturnBadRequestWhenBadRequestIsThrown() throws Exception {
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
        OfferDto offerDto = offerService.saveOffer(offerDtoTest);
        offerDto.setPrice(700000);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/offers/{id}", offerDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(jsonPath("$.price").value(700000))
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
        OfferDto offerDto = offerService.saveOffer(offerDtoTest);
        offerDto.setPrice(700000);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/offers/{id}", 10000L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers
                        .content().string("{\"status\":\"NOT_FOUND\",\"message\":\"404 NOT_FOUND \\\"Offer not found\\\"\"}"));
    }

    @Test
    @Transactional
    void shouldDeleteOfferWhenOfferExist() throws Exception {
        //given
        OfferDto offerDtoTest = getOfferDtoData();
        OfferDto offerDto = offerService.saveOffer(offerDtoTest);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.delete("/offers/{id}", offerDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDto)))
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
        OfferDto offerDto = offerService.saveOffer(offerDtoTest);
        ClientMessageDto clientMessageDtoTest = new ClientMessageDto();
        clientMessageDtoTest.setMessage("Message");
        clientMessageDtoTest.setIdOffer(offerDto.getId());
        clientMessageDtoTest.setName("XYZ");
        clientMessageDtoTest.setEmail("xyz@gmail.com");
        clientMessageDtoTest.setPhoneNumber("900800700");

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/offers/{id}/message", offerDto.getId())
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
        ClientMessageDto clientMessageDtoTest = new ClientMessageDto();
        clientMessageDtoTest.setMessage("Message");
        clientMessageDtoTest.setIdOffer(notExistentId);
        clientMessageDtoTest.setName("XYZ");
        clientMessageDtoTest.setEmail("xyz@gmail.com");
        clientMessageDtoTest.setPhoneNumber("900800700");

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
        ClientMessageDto clientMessageDto = new ClientMessageDto();

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/offers/{id}/message", offerDtoTest.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientMessageDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    private static Offer getOfferData() {
        Offer testOffer = new Offer();
        OfferDetails testOfferDetails = getOfferDetailsData();

        testOffer.setOfferDetails(testOfferDetails);
        testOffer.setKindOfProperty(Offer.KindOfProperty.MIESZKANIE);
        testOffer.setPrice(825000);
        testOffer.setTitle("Super lokalizacja/trzy pokoje/dwupoziomowe/parking");
        testOffer.setCity("Wroclaw");
        testOffer.setStreet("Dluga");
        testOffer.setNumberOfRooms(4);
        testOffer.setFloor(3);
        testOffer.setDescription("Sprzedam mieszkanie w centrum Wroclawia");
        return testOffer;
    }

    private static OfferDetails getOfferDetailsData() {
        OfferDetails testOfferDetails = new OfferDetails();
        OfferDetails.AdditionalInformation testAdditionalInformation = getAdditionalInformationData();

        testOfferDetails.setAdditionalInformation(testAdditionalInformation);
        testOfferDetails.setRent(500);
        testOfferDetails.setOwnershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC);
        testOfferDetails.setFinishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA);
        testOfferDetails.setParkingPlace(OfferDetails.ParkingPlace.BRAK);
        testOfferDetails.setHeating(OfferDetails.Heating.ELEKTRYCZNE);
        testOfferDetails.setContactDetails("555444333");
        return testOfferDetails;
    }

    private static OfferDetails.AdditionalInformation getAdditionalInformationData() {
        OfferDetails.AdditionalInformation testAdditionalInformation = new OfferDetails.AdditionalInformation();

        testAdditionalInformation.setMarket(OfferDetails.Market.PIERWOTNY);
        testAdditionalInformation.setAnnouncerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI);
        testAdditionalInformation.setYearOfConstruction(2022);
        testAdditionalInformation.setBuildingType(OfferDetails.BuildingType.APARTAMENTOWIEC);
        testAdditionalInformation.setMedia("Tv, internet");
        testAdditionalInformation.setEquipment("lodowka, piekarnik, kuchenka");
        return testAdditionalInformation;
    }

    private static OfferDto getOfferDtoData() {
        OfferDto testOfferDto = new OfferDto();
        OfferDetailsDto testOfferDetailsDto = getOfferDetailsDtoData();

        testOfferDto.setOfferDetailsDto(testOfferDetailsDto);
        testOfferDto.setKindOfProperty(Offer.KindOfProperty.MIESZKANIE);
        testOfferDto.setPrice(825000);
        testOfferDto.setTitle("Super lokalizacja/trzy pokoje/dwupoziomowe/parking");
        testOfferDto.setCity("Wroclaw");
        testOfferDto.setStreet("Dluga");
        testOfferDto.setNumberOfRooms(4);
        testOfferDto.setFloor(3);
        testOfferDto.setDescription("Sprzedam mieszkanie w centrum Wroclawia");
        return testOfferDto;
    }

    private static OfferDetailsDto getOfferDetailsDtoData() {
        OfferDetailsDto testOfferDetailsDto = new OfferDetailsDto();
        OfferDetailsDto.AdditionalInformation testAdditionalInformationDto = getAdditionalInformationDtoData();

        testOfferDetailsDto.setAdditionalInformationDto(testAdditionalInformationDto);
        testOfferDetailsDto.setRent(500);
        testOfferDetailsDto.setOwnershipForm(OfferDetails.OwnershipForm.PELNA_WLASNOSC);
        testOfferDetailsDto.setFinishLevel(OfferDetails.FinishLevel.DO_ZAMIESZKANIA);
        testOfferDetailsDto.setParkingPlace(OfferDetails.ParkingPlace.BRAK);
        testOfferDetailsDto.setHeating(OfferDetails.Heating.ELEKTRYCZNE);
        testOfferDetailsDto.setContactDetails("555444333");
        return testOfferDetailsDto;
    }

    private static OfferDetailsDto.AdditionalInformation getAdditionalInformationDtoData() {
        OfferDetailsDto.AdditionalInformation testAdditionalInformationDto = new OfferDetailsDto.AdditionalInformation();

        testAdditionalInformationDto.setMarket(OfferDetails.Market.PIERWOTNY);
        testAdditionalInformationDto.setAnnouncerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI);
        testAdditionalInformationDto.setYearOfConstruction(2022);
        testAdditionalInformationDto.setBuildingType(OfferDetails.BuildingType.APARTAMENTOWIEC);
        testAdditionalInformationDto.setMedia("Tv, internet");
        testAdditionalInformationDto.setEquipment("lodowka, piekarnik, kuchenka");
        return testAdditionalInformationDto;
    }

}