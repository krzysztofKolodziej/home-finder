package com.aplication.homeFinder.offer.controller;

import com.aplication.homeFinder.offer.model.Offer;
import com.aplication.homeFinder.offer.model.OfferDetails;
import com.aplication.homeFinder.offer.service.FilteringSchema;
import com.aplication.homeFinder.offer.service.dto.ClientMessageDto;
import com.aplication.homeFinder.offer.service.dto.OfferDto;
import com.aplication.homeFinder.offer.service.OfferService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@AllArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @GetMapping("/offers")
    public ResponseEntity<?> findAllOffer(@ModelAttribute FilteringSchema filteringSchema) {
        try {
            List<OfferDto> offers = offerService.findOffers(filteringSchema);
            return ResponseEntity.status(HttpStatus.OK).body(offers);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @GetMapping("/offers/{id}")
    public ResponseEntity<?> findOfferWithDetails(@PathVariable @Valid Long id) {
        try {
            OfferDto offerDto = offerService.findOfferWithDetails(id);
            return ResponseEntity.status(HttpStatus.OK).body(offerDto);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> safeOffer(@RequestBody @Valid OfferDto offerDto) {
        try {
            OfferDto offerDtoMap = offerService.saveOffer(offerDto);
            return ResponseEntity.status(HttpStatus.OK).body(offerDtoMap);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @PutMapping("/offers/{id}")
    public ResponseEntity<?> updateOffer(@RequestBody @Valid OfferDto offerDto, @PathVariable Long id) {
        try {
            OfferDto offerDtoMap = offerService.updateOffer(offerDto, id);
            return ResponseEntity.status(HttpStatus.OK).body(offerDtoMap);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @DeleteMapping("/offers/{id}")
    public Long deleteOffer(@PathVariable @Valid Long id) {
        offerService.deleteOffer(id);
        return id;
    }

    @PostMapping("offers/{id}/message")
    public ResponseEntity<String> saveMessage(@RequestBody @Valid ClientMessageDto clientMessageDto, @PathVariable Long id) {
        try {
            offerService.addMessage(clientMessageDto, id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
