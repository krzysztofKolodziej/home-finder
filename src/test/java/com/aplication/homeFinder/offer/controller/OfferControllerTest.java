package com.aplication.homeFinder.offer.controller;

import com.aplication.homeFinder.offer.model.Offer;
import com.aplication.homeFinder.offer.model.OfferDetails;
import com.aplication.homeFinder.offer.repository.OfferRepository;
import com.aplication.homeFinder.offer.service.dto.OfferDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
class OfferControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OfferRepository offerRepository;


    @Test
    @Transactional
    void shouldReturnOfferWithDetailsWhenOfferExists() throws Exception {
        //given
        Offer testOffer = new Offer();
        OfferDetails testOfferDetails = new OfferDetails();
        OfferDetails.AdditionalInformation testAdditionalInformation = new OfferDetails.AdditionalInformation();

        testAdditionalInformation.setAnnouncerType(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI);
        testAdditionalInformation.setEquipment("lodowka, piekarnik, kuchenka");

        testOfferDetails.setAdditionalInformation(testAdditionalInformation);
        testOfferDetails.setParkingPlace(OfferDetails.ParkingPlace.BRAK);
        testOfferDetails.setRent(500);

        testOffer.setOfferDetails(testOfferDetails);
        testOffer.setPrice(825000);
        testOffer.setTitle("Super lokalizacja/trzy pokoje/dwupoziomowe/parking");
        testOffer.setFloor(3);

        offerRepository.save(testOffer);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/offers/" + testOffer.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        //then
        OfferDto offerDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), OfferDto.class);

        assertThat(offerDto.getOfferDetailsDto().getAdditionalInformationDto()).isNotNull();
        assertThat(offerDto.getOfferDetailsDto().getAdditionalInformationDto().getAnnouncerType())
                .isEqualTo(OfferDetails.AnnouncerType.BIURO_NIERUCHOMOSCI);
        assertThat(offerDto.getOfferDetailsDto().getAdditionalInformationDto().getEquipment())
                .isEqualTo("lodowka, piekarnik, kuchenka");

        assertThat(offerDto.getOfferDetailsDto()).isNotNull();
        assertThat(offerDto.getOfferDetailsDto().getParkingPlace()).isEqualTo(OfferDetails.ParkingPlace.BRAK);
        assertThat(offerDto.getOfferDetailsDto().getRent()).isEqualTo(500);

        assertThat(offerDto).isNotNull();
        assertThat(offerDto.getId()).isEqualTo(testOffer.getId());
        assertThat(offerDto.getPrice()).isEqualTo(825000);
        assertThat(offerDto.getTitle()).isEqualTo("Super lokalizacja/trzy pokoje/dwupoziomowe/parking");
        assertThat(offerDto.getFloor()).isEqualTo(3);
    }

    @Test
    void shouldReturnBadRequestWhenBadRequestIsThrown() throws Exception {
        //given
        String badRequest = "bad request";

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/offers/" + badRequest))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    void shouldReturnNotFoundWhenOfferDoesNotExist() throws Exception {
        //given
        long notExistentId = 9999999999L;

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/offers/" + notExistentId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("404 NOT_FOUND \"Offer not found\""));
    }

}