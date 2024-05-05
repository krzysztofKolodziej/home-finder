package com.aplication.homeFinder.offer;

import com.aplication.homeFinder.offer.model.Offer;
import com.aplication.homeFinder.offer.service.dto.OfferDetailsDto;
import com.aplication.homeFinder.offer.service.dto.OfferDto;
import com.aplication.homeFinder.offer.service.dto.OfferRequest;
import com.aplication.homeFinder.offer.service.OfferService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
public class OfferController {

    private final OfferService offerService;
    public static final Long EMPTY_ID = null;

    @GetMapping("/offers")
    public ResponseEntity<String> findAllOffer() {
        try {
            offerService.findOffers();
            return ResponseEntity.status(HttpStatus.OK).body("znaleziono oferty");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @GetMapping("/offers/{id}")
    public ResponseEntity<String> findOffer(@PathVariable @Valid Long id) {
        try {
            offerService.findOffer(id);
            return ResponseEntity.status(HttpStatus.OK).body("znaleziono ofertę");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @PostMapping("/offers")
    public ResponseEntity<String> safeOffer(@RequestBody @Valid OfferRequest offerRequest) {
        OfferDetailsDto offerDetailsDto = offerRequest.getOfferDetailsDto();
        OfferDto offerDto = offerRequest.getOfferDto();
        try {
            Offer offer = offerService.saveOffer(offerDto, offerDetailsDto, EMPTY_ID);
            return ResponseEntity.status(HttpStatus.OK).body("dodano ofertę " + offer.getId());
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @PutMapping("/offers/{id}") // TODO: 27.04.2024 aktualizacja tylko ofert od użytkownika
    public ResponseEntity<String> updateOffer(@RequestBody @Valid OfferRequest offerRequest, Long id) {
        OfferDetailsDto offerDetailsDto = offerRequest.getOfferDetailsDto();
        OfferDto offerDto = offerRequest.getOfferDto();
        try {
            offerService.updateOffer(offerDto, offerDetailsDto, id);
            return ResponseEntity.status(HttpStatus.OK).body("dodano ofertę");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @DeleteMapping("/offers/{id}") // TODO: 27.04.2024 usuwanie tylko ofert od użytkownika
    public ResponseEntity<String> deleteOffer(@PathVariable @Valid Long id) {
        offerService.deleteOffer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("usunięto ofertę");
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {
//
//        Map<String, String> errors = new HashMap<>();
//
//        exception.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return errors;
//    }
}
