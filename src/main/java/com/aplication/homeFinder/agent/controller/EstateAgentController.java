package com.aplication.homeFinder.agent.controller;

import com.aplication.homeFinder.agent.service.EstateAgentDto;
import com.aplication.homeFinder.agent.service.EstateAgentService;
import com.aplication.homeFinder.errorHandler.GlobalExceptionHandler;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/agents")
public class EstateAgentController {

    private final EstateAgentService estateAgentService;

    @PostMapping
    public ResponseEntity<String> addAgent(@RequestBody @Valid EstateAgentDto estateAgentDto)
            throws GlobalExceptionHandler {
        estateAgentService.addAgent(estateAgentDto);
        return ResponseEntity.status(HttpStatus.OK).body("Estate agent added");
    }

    @GetMapping
    public ResponseEntity<List<EstateAgentDto>> findAgents() throws GlobalExceptionHandler {
        List<EstateAgentDto> agents = estateAgentService.findAgent();
        return ResponseEntity.status(HttpStatus.OK).body(agents);
    }
}
