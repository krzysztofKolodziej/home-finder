package com.aplication.homeFinder.offer.model;

import com.aplication.homeFinder.offer.model.enumClass.KindOfProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private KindOfProperty kindOfProperty;
    private double price;
    private String title;
    private String location;
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

    public void addDetails(OfferDetails offerDetails) {
        offerDetails.setOffer(this);
        this.offerDetails = offerDetails;
    }
    public void removeDetails() {
        offerDetails.setOffer(null);
        this.offerDetails = null;
    }
}
