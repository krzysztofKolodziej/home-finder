package com.aplication.homeFinder.offer.model;

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
    private KindOfProperty kindOfProperty;
    private double price;
    private String title;
    private String city;
    private String street;
    private int numberOfRooms;
    private double area;
    private double pricePerMeter;
    private int floor;
    private String description;
    @OneToOne(
            mappedBy = "offer",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private OfferDetails offerDetails;
    @OneToMany(
            mappedBy = "offer",
            cascade = CascadeType.ALL
    )
    private List<ClientMessage> clientMessages = new ArrayList<>();

    public enum KindOfProperty {
        MIESZKANIE, DOM, DZIALKA, LOKAL, GARAZ
    }
}
