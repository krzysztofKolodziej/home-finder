package com.aplication.homeFinder.offer.service;

import com.aplication.homeFinder.offer.model.ClientMessage;
import com.aplication.homeFinder.offer.model.Currency;
import com.aplication.homeFinder.offer.model.Offer;
import com.aplication.homeFinder.offer.model.OfferDetails;
import com.aplication.homeFinder.offer.repository.ClientMessageRepository;
import com.aplication.homeFinder.offer.repository.OfferRepository;
import com.aplication.homeFinder.offer.service.dto.ClientMessageDto;
import com.aplication.homeFinder.offer.service.dto.OfferDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfferServiceTest {

    @Mock
    private OfferRepository offerRepository;
    @Mock
    private ClientMessageRepository clientMessageRepository;
    @Mock
    private ExchangeClient exchangeClient;
    @Mock
    private FilteringLogic filteringLogic;
    @Mock
    private FilteringSchema filteringSchema;
    @Mock
    private Mapper mapper;
    @InjectMocks
    private OfferService offerService;

    @Test
    void shouldReturnFilteredOffers() {
        //given
        List<Offer> offerList = getOfferList();
        OfferDto offerDtoTest = new OfferDto();
        final String currency = "eur";
        final double midRate = 1d;

        Currency currencyTest = mock(Currency.class);
        when(currencyTest.getMidRate()).thenReturn(midRate);

        when(filteringLogic.filteringMethod(filteringSchema)).thenReturn(offerList);
        when(exchangeClient.getExchangeRate(currency)).thenReturn(currencyTest);
        when(mapper.mapOfferDto(offerList.get(0), midRate)).thenReturn(offerDtoTest);

        //when
        List<OfferDto> result = offerService.findOffers(filteringSchema, currency);

        //then
        assertEquals(3, result.size());
        assertEquals(offerDtoTest, result.get(0));
        verify(filteringLogic).filteringMethod(filteringSchema);
    }

    @Test
    void shouldReturnOfferWithDetailsWhenOfferExists() {
        //given
        long id = 1L;

        OfferDto offerDtoTest = new OfferDto();
        OfferDetails offerDetailsTest = new OfferDetails();
        Offer offerTest = Offer.builder()
                .id(id)
                .offerDetails(offerDetailsTest)
                .build();

        when(offerRepository.findById(id)).thenReturn(Optional.of(offerTest));
        when(mapper.mapOfferWithDetailsDto(offerTest)).thenReturn(offerDtoTest);

        //when
        OfferDto offerDtoResult = offerService.findOfferWithDetails(id);

        //then
        assertNotNull(offerDtoResult);
        assertEquals(offerDtoTest, offerDtoResult);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenOfferDoesNotExist() {
        //given
        Long id = 1L;

        when(offerRepository.findById(id)).thenReturn(Optional.empty());

        //when & then
        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class,
                () -> offerService.findOfferWithDetails(id));
        assertEquals(HttpStatus.NOT_FOUND, responseStatusException.getStatusCode());
        assertEquals("404 NOT_FOUND \"Offer not found\"", responseStatusException.getMessage());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenOfferDetailsDoesNotExist() {
        //given
        Long id = 1L;

        Offer offer = Offer.builder()
                .id(id)
                .offerDetails(null)
                .build();

        when(offerRepository.findById(id)).thenReturn(Optional.of(offer));

        //when & then
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

        when(mapper.mapOffer(offerDtoInput)).thenReturn(offerInput);
        when(offerRepository.save(offerInput)).thenReturn(offerOutput);
        when(mapper.mapOfferWithDetailsDto(offerOutput)).thenReturn(offerDtoOutput);

        //when
        OfferDto offerDtoTest = offerService.saveOffer(offerDtoInput);

        //then
        assertEquals(offerDtoTest, offerDtoOutput);
        verify(offerRepository).save(offerInput);
    }

    @Test
    void shouldUpdateOfferDtoAndReturnOfferDto() {
        // given
        Long offerId = 1L;
        OfferDto offerDto = new OfferDto();
        Offer existingOffer = new Offer();
        Offer updatedOffer = new Offer();

        when(offerRepository.findById(offerId)).thenReturn(Optional.of(existingOffer));
        when(mapper.mapOfferEdit(existingOffer, offerDto)).thenReturn(updatedOffer);
        when(offerRepository.save(updatedOffer)).thenReturn(updatedOffer);

        // when
        OfferDto result = offerService.updateOffer(offerDto, offerId);

        // then
        assertEquals(offerDto, result);
        verify(offerRepository).findById(offerId);
        verify(mapper).mapOfferEdit(existingOffer, offerDto);
        verify(offerRepository).save(updatedOffer);
    }

    @Test
    void shouldThrowExceptionWhenOfferNotFound() {
        // given
        Long offerId = 1L;
        OfferDto offerDtoTest = new OfferDto();

        when(offerRepository.findById(offerId)).thenReturn(Optional.empty());

        // when & then
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> offerService.updateOffer(offerDtoTest, offerId));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("404 NOT_FOUND \"Offer not found\"", exception.getMessage());
    }

    @Test
    void shouldDeleteOffer() {
        //given
        final long id = 1L;

        //when
        offerService.deleteOffer(id);

        //then
        verify(offerRepository).deleteById(id);
    }

    @Test
    void shouldSaveClientMessageDtoWhenOfferIdExist() {
        //given
        final long id = 1L;
        ClientMessageDto clientMessageDtoTest = new ClientMessageDto();
        ClientMessage clientMessage = new ClientMessage();
        Offer offerTest = new Offer();

        when(offerRepository.findById(id)).thenReturn(Optional.of(offerTest));
        when(mapper.mapClientMessage(clientMessageDtoTest, offerTest)).thenReturn(clientMessage);

        //when
        offerService.addMessage(clientMessageDtoTest, id);

        //then
        verify(offerRepository).findById(id);
        verify(clientMessageRepository).save(clientMessage);
    }

    @Test
    void shouldThrowExceptionWhenOfferNotFoundAddMessage() {
        // given
        Long offerId = 1L;
        ClientMessageDto clientMessageDtoTest = new ClientMessageDto();

        when(offerRepository.findById(offerId)).thenReturn(Optional.empty());

        // when & then
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> offerService.addMessage(clientMessageDtoTest, offerId));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("404 NOT_FOUND \"Offer not found\"", exception.getMessage());
    }


    private static List<Offer> getOfferList() {
        Offer offerTest = Offer.builder()
                .id(1L)
                .kindOfProperty(Offer.KindOfProperty.MIESZKANIE)
                .price(500000d)
                .title("Sprzedam mieszkanie")
                .city("Wrocław")
                .numberOfRooms(3)
                .area(50d)
                .pricePerMeter(10000d)
                .floor(3)
                .description("Sprzedam mieszkanie w spokojnej okolicy")
                .build();

        Offer offerTest2 = Offer.builder()
                .id(10L)
                .kindOfProperty(Offer.KindOfProperty.DOM)
                .price(5000000d)
                .title("Sprzedam dom")
                .city("Wrocław")
                .numberOfRooms(4)
                .area(500d)
                .pricePerMeter(10000d)
                .floor(3)
                .description("Sprzedam dom w spokojnej okolicy")
                .build();

        Offer offerTest3 = Offer.builder()
                .id(155L)
                .kindOfProperty(Offer.KindOfProperty.DZIALKA)
                .price(100000d)
                .title("Sprzedam działkę budowlaną niedaleko Wrocławia")
                .city("Wrocław")
                .numberOfRooms(3)
                .area(1000d)
                .pricePerMeter(100d)
                .floor(0)
                .description("Sprzedam działkę niedaleko Wrocławia")
                .build();

        return Arrays.asList(offerTest, offerTest2, offerTest3);
    }

}