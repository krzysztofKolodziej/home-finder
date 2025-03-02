package com.aplication.homeFinder.offer.controller;

import com.aplication.homeFinder.offer.service.FilteringSchema;
import com.aplication.homeFinder.offer.service.OfferService;
import com.aplication.homeFinder.offer.service.dto.ClientMessageDto;
import com.aplication.homeFinder.offer.service.dto.OfferDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @GetMapping("/offers/{currency}/all")
    public ResponseEntity<List<OfferDto>> findAllOffer(@ModelAttribute FilteringSchema filteringSchema,
                                                       @PathVariable String currency) {
        List<OfferDto> offersDto = offerService.findOffers(filteringSchema, currency);
        return ResponseEntity.status(HttpStatus.OK).body(offersDto);
    }

    @GetMapping("/offers/{id}")
    public ResponseEntity<OfferDto> findOfferWithDetails(@PathVariable @Valid Long id) {
        OfferDto offerDto = offerService.findOfferWithDetails(id);
        return ResponseEntity.status(HttpStatus.OK).body(offerDto);
    }

    @PostMapping("/add")
    public ResponseEntity<OfferDto> safeOffer(@RequestBody @Valid OfferDto offerDto) {
        OfferDto offerDtoMap = offerService.saveOffer(offerDto);
        return ResponseEntity.status(HttpStatus.OK).body(offerDtoMap);
    }

    @PutMapping("/offers/{id}")
    public ResponseEntity<OfferDto> updateOffer(@RequestBody @Valid OfferDto offerDto, @PathVariable Long id) {
        OfferDto offerDtoMap = offerService.updateOffer(offerDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(offerDtoMap);
    }

    @DeleteMapping("/offers/{id}")
    public ResponseEntity<Long> deleteOffer(@PathVariable @Valid Long id) {
        offerService.deleteOffer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(id);
    }

    @PostMapping("offers/{id}/message")
    public ResponseEntity<Object> saveMessage(@RequestBody @Valid ClientMessageDto clientMessageDto, @PathVariable Long id) {
        offerService.addMessage(clientMessageDto, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
