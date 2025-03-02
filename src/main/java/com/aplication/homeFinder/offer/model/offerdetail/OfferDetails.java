package com.aplication.homeFinder.offer.model.offerdetail;

import com.aplication.homeFinder.offer.model.Offer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private double rent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OwnershipForm ownershipForm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FinishLevel finishLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParkingPlace parkingPlace;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Heating heating;

    @Column(nullable = false)
    private String contactDetails;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @Embedded
    private AdditionalInformation additionalInformation;

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class AdditionalInformation{

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private Market market;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private AnnouncerType announcerType;

        @Min(0)
        @Column(nullable = false)
        private int yearOfConstruction;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private BuildingType buildingType;

        @Column(nullable = false)
        private String media;

        @Column(nullable = false)
        private String equipment;
    }
}
