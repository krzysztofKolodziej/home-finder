package com.aplication.homeFinder.offer.model;

import com.aplication.homeFinder.offer.model.offerdetail.OfferDetails;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KindOfProperty kindOfProperty;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private int numberOfRooms;

    @Column(nullable = false)
    private double area;

    @Column(nullable = false)
    private double pricePerMeter;

    @Column(nullable = false)
    private int floor;

    @Column(nullable = false)
    private String description;

    @OneToOne(mappedBy = "offer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private OfferDetails offerDetails;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL)
    private List<ClientMessage> clientMessages = new ArrayList<>();
}
