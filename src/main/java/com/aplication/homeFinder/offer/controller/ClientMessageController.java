package com.aplication.homeFinder.offer.controller;

import com.aplication.homeFinder.offer.service.ClientMessageService;
import com.aplication.homeFinder.offer.service.dto.ClientMessageDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
public class ClientMessageController {

    private final ClientMessageService clientMessageService;


}
