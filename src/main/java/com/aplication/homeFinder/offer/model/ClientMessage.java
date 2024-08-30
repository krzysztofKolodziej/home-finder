package com.aplication.homeFinder.offer.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String message;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id")
    private Offer offer;
}
