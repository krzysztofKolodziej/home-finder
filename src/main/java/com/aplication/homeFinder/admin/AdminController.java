package com.aplication.homeFinder.admin;

import com.aplication.homeFinder.offer.model.ClientMessage;
import com.aplication.homeFinder.offer.service.dto.ClientMessageDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin/clientMessages")
    public ResponseEntity<?> findClientMessage() {
        List<ClientMessageDto> clientMessageDto = adminService.findClientMessage();
        try {
            return ResponseEntity.status(HttpStatus.OK).body(clientMessageDto);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }
}
