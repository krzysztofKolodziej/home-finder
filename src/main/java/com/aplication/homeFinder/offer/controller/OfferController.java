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
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    @GetMapping
    public ResponseEntity<?> findAllOffer(@ModelAttribute FilteringSchema filteringSchema) {
        try {
            List<OfferDto> offers = offerService.findOffers(filteringSchema);
            return ResponseEntity.status(HttpStatus.OK).body(offers);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/")
    public ResponseEntity<?> findOfferWithDetails(@PathVariable @Valid Long id) {
        try {
            OfferDto offerDto = offerService.findOfferWithDetails(id);
            return ResponseEntity.status(HttpStatus.OK).body(offerDto);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> safeOffer(@RequestBody @Valid OfferDto offerDto) {
        try {
            Offer offer = offerService.saveOffer(offerDto);
            return ResponseEntity.status(HttpStatus.OK).body("dodano ofertę " + offer.getId());
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateOffer(@RequestBody @Valid OfferDto offerDto, @PathVariable Long id) {
        try {
            Offer offer = offerService.updateOffer(offerDto, id);
            return ResponseEntity.status(HttpStatus.OK).body("zaktualizowano ofertę " + offer.getId());
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public void deleteOffer(@PathVariable @Valid Long id) {
        offerService.deleteOffer(id);
    }

    @PostMapping("/{id}/message")
    public ResponseEntity<String> saveMessage(@RequestBody @Valid ClientMessageDto clientMessageDto, @PathVariable Long id) {
        try {
            offerService.addMessage(clientMessageDto, id);
            return ResponseEntity.status(HttpStatus.OK).body("Dziękujemy za przesłaną wiadomość");
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
