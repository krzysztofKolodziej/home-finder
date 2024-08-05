package com.aplication.homeFinder.offer.service;

import com.aplication.homeFinder.offer.model.Offer;
import com.aplication.homeFinder.offer.model.OfferDetails;
import com.aplication.homeFinder.offer.repository.OfferRepository;
import com.aplication.homeFinder.offer.service.dto.OfferDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfferServiceTest {

    @Mock
    OfferRepository offerRepository;
    @Mock
    Mapper mapper;
    @InjectMocks
    OfferService offerService;

    @Test
    void shouldReturnOfferWithDetailsWhenOfferExists() {
        //given
        Long id = 1L;
        Offer offerTest = new Offer();
        OfferDetails offerDetailsTest = new OfferDetails();
        offerTest.setId(id);
        offerTest.setOfferDetails(offerDetailsTest);
        OfferDto offerDtoTest = new OfferDto();

        //when
        when(offerRepository.findById(id)).thenReturn(Optional.of(offerTest));
        when(mapper.mapOfferWithDetailsDto(offerTest)).thenReturn(offerDtoTest);

        OfferDto offerDtoResult = offerService.findOfferWithDetails(id);

        //then
        assertNotNull(offerDtoResult);
        assertEquals(offerDtoTest, offerDtoResult);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenOfferDoesNotExist() {
        //given
        Long id = 1L;

        //when
        when(offerRepository.findById(id)).thenReturn(Optional.empty());

        //then
        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class,
                () -> offerService.findOfferWithDetails(id));
        assertEquals(HttpStatus.NOT_FOUND, responseStatusException.getStatusCode());
        assertEquals("404 NOT_FOUND \"Offer not found\"", responseStatusException.getMessage());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenOfferDetailsDoesNotExist() {
        //given
        Long id = 1L;
        Offer offer = new Offer();
        offer.setId(id);
        offer.setOfferDetails(null);

        //when
        when(offerRepository.findById(id)).thenReturn(Optional.of(offer));

        //then
        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class,
                () -> offerService.findOfferWithDetails(id));
        assertEquals(HttpStatus.NOT_FOUND, responseStatusException.getStatusCode());
        assertEquals("404 NOT_FOUND \"Offer Details not found\"", responseStatusException.getMessage());
    }

    @Test
    void shouldSaveOfferAndReturnOfferDTo() {
        //given
        OfferDto offerDtoInput = new OfferDto();
        OfferDto offerDtoOutput = new OfferDto();
        Offer offerInput = new Offer();
        Offer offerOutput = new Offer();

        //when
        when(mapper.mapOffer(offerDtoInput)).thenReturn(offerInput);
        when(offerRepository.save(offerInput)).thenReturn(offerOutput);
        when(mapper.mapOfferWithDetailsDto(offerOutput)).thenReturn(offerDtoOutput);

        OfferDto offerDtoTest = offerService.saveOffer(offerDtoInput);

        //then
        assertEquals(offerDtoTest, offerDtoOutput);
        verify(offerRepository).save(offerInput);
    }

}