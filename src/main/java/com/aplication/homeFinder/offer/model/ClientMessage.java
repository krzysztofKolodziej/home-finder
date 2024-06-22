package com.aplication.homeFinder.offer.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Bean;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String message;
    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;
}
