package com.aplication.homeFinder.offer.controller;

import com.aplication.homeFinder.errorHandler.ErrorRespond;
import com.aplication.homeFinder.errorHandler.GlobalExceptionHandler;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @GetMapping("/offers/{currency}/all")
    public ResponseEntity<List<OfferDto>> findAllOffer(@ModelAttribute FilteringSchema filteringSchema,
                                                       @PathVariable String currency) throws GlobalExceptionHandler {
        List<OfferDto> offersDto = offerService.findOffers(filteringSchema, currency);
        return ResponseEntity.status(HttpStatus.OK).body(offersDto);
    }

    @GetMapping("/offers/{id}")
    public ResponseEntity<OfferDto> findOfferWithDetails(@PathVariable @Valid Long id) throws GlobalExceptionHandler {
        OfferDto offerDto = offerService.findOfferWithDetails(id);
        return ResponseEntity.status(HttpStatus.OK).body(offerDto);
    }

    @PostMapping("/add")
    public ResponseEntity<OfferDto> safeOffer(@RequestBody @Valid OfferDto offerDto) throws GlobalExceptionHandler {
        OfferDto offerDtoMap = offerService.saveOffer(offerDto);
        return ResponseEntity.status(HttpStatus.OK).body(offerDtoMap);
    }

    @PutMapping("/offers/{id}")
    public ResponseEntity<?> updateOffer(@RequestBody @Valid OfferDto offerDto, @PathVariable Long id)
            throws GlobalExceptionHandler {
        OfferDto offerDtoMap = offerService.updateOffer(offerDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(offerDtoMap);
    }

    @DeleteMapping("/offers/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable @Valid Long id) {
        try {
            offerService.deleteOffer(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(id);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return new ResponseEntity<>(new ErrorRespond(e.getStatusCode(), e.getMessage()), e.getStatusCode());
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ErrorRespond(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("offers/{id}/message")
    public ResponseEntity<?> saveMessage(@RequestBody @Valid ClientMessageDto clientMessageDto, @PathVariable Long id) {
        try {
            offerService.addMessage(clientMessageDto, id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return new ResponseEntity<>(new ErrorRespond(e.getStatusCode(), e.getMessage()), e.getStatusCode());
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ErrorRespond(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
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
