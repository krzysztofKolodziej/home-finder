package com.aplication.homeFinder.admin;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
@AllArgsConstructor
@RestController
public class AdminOfferController {

    private final AdminOfferService adminOfferService;
    public static final Long EMPTY_ID = null;

    @GetMapping("/admin/offers")
    public ResponseEntity<String> findAllOffer(Pageable pageable) {
        try {
            adminOfferService.findOffers(pageable);
            return ResponseEntity.status(HttpStatus.OK).body("znaleziono oferty");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }
    @GetMapping("/admin/offers/{id}")
    public ResponseEntity<String> findOffer(@PathVariable @Valid Long id) {
        try {
            adminOfferService.findOffer(id);
            return ResponseEntity.status(HttpStatus.OK).body("znaleziono ofertę");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }
    @PostMapping("/admin/offers")
    public ResponseEntity<String> saveOffer(@RequestBody @Valid AdminOfferDto adminOfferDto) {
        try {
            adminOfferService.saveOffer(adminOfferDto, EMPTY_ID);
            return ResponseEntity.status(HttpStatus.OK).body("dodano ofertę");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }
    @PutMapping("/admin/offers/{id}")
    public ResponseEntity<String> updateOffer(@RequestBody @Valid AdminOfferDto adminOfferDto,@PathVariable @Valid Long id) {
        try {
            adminOfferService.saveOffer(adminOfferDto, id);
            return ResponseEntity.status(HttpStatus.OK).body("zaktualizowano ofertę");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }
    @DeleteMapping("/admin/offers/{id}")
    public ResponseEntity<String> deleteOffer(@PathVariable @Valid Long id) {
        adminOfferService.deleteOffer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("usunięto ofertę");
    }
}
