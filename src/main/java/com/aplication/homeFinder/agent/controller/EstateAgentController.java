package com.aplication.homeFinder.agent.controller;

import com.aplication.homeFinder.agent.service.EstateAgentDto;
import com.aplication.homeFinder.agent.service.EstateAgentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor
// dodać requestBody
public class EstateAgentController {

    private final EstateAgentService estateAgentService;

    @PostMapping("/agents")
    public ResponseEntity<?> addAgent(@RequestBody @Valid EstateAgentDto estateAgentDto) {
        try {
            estateAgentService.addAgent(estateAgentDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Pomyslnie dodano agenta nieruchomosci");
        } catch (ResponseStatusException e) {
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(e.getMessage());
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (HttpServerErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }/* catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR); */
            // internar server error

    }
    @GetMapping("/agents")
    public ResponseEntity<?> findAgents() {
        try {
            List<EstateAgentDto> agent = estateAgentService.findAgent();
            return ResponseEntity.status(HttpStatus.OK).body(agent);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @DeleteMapping("/agents")
    public ResponseEntity<String> deleteAgent(Long id) {
        try {
            estateAgentService.deleteAgent(id);
            return ResponseEntity.status(HttpStatus.OK).body("Agent został usuniety " + id);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

}
