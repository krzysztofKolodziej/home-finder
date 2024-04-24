package com.aplication.homeFinder.offer;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class OfferController {

    private final OfferService offerService;
    public static final Long EMPTY_ID = null;

    @GetMapping("/offers")
    public ResponseEntity<String> findAllOffer(Pageable pageable) {
        try {
            offerService.findOffers(pageable);
            return ResponseEntity.status(HttpStatus.OK).body("znaleziono oferty");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }
    @GetMapping("/findOffers/{id}")
    public ResponseEntity<String> findOffer(@PathVariable @Valid Long id) {
        try {
            offerService.findOffer(id);
            return ResponseEntity.status(HttpStatus.OK).body("znaleziono ofertę");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }
    @PostMapping("/addOffers")
    public ResponseEntity<String> addOffer(@RequestBody @Valid OfferDto offerDto) {
        try {
            offerService.saveOffer(offerDto, EMPTY_ID);
            return ResponseEntity.status(HttpStatus.OK).body("dodano ofertę");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }
    @DeleteMapping("/deleteOffers/{id}")
    public ResponseEntity<String> deleteOffer(@PathVariable @Valid Long id) {
        offerService.deleteOffer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("usunięto książkę");
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
