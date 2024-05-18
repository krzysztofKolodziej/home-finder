package com.aplication.homeFinder.offer.model;

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
    private Long id;
    private double rent;
    @Enumerated(EnumType.STRING)
    private OwnershipForm ownershipForm;
    @Enumerated(EnumType.STRING)
    private FinishLevel finishLevel;
    @Enumerated(EnumType.STRING)
    private ParkingPlace parkingPlace;
    @Enumerated(EnumType.STRING)
    private Heating heating;
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
    public static class AdditionalInformation{
        @Enumerated(EnumType.STRING)
        private Market market;
        @Enumerated(EnumType.STRING)
        private AnnouncerType announcerType;
        @Min(0)
        private int yearOfConstruction;
        @Enumerated(EnumType.STRING)
        private BuildingType buildingType;
        @NotNull
        private String media;
        @NotNull
        private String equipment;
    }
    public enum AnnouncerType {
        DEWELOPER, BIURONIERUCHOMOSCI, OSOBAPRYWATNA
    }
    public enum BuildingType {
        BLOK, KAMIENICA, DOMWOLNOSTOJACY, SZEREGOWIEC, APARTAMENTOWIEC
    }
    public enum FinishLevel {
        DOZAMIESZKANIA, DOWYKONCZENIA, DOREMONTU
    }
    public enum Heating {
        MIEJSKIE, GAZOWE, ELEKTRYCZNE, KOTLOWE, INNE
    }
    public enum Market {
        PIERWOTNY, WTORNY
    }
    public enum OwnershipForm {
        PELNAWLASNOSC, SPOLDZIELCZE, UZYTKOWANIEWIECZYSTE, UDZIAL
    }
    public enum ParkingPlace {
        BRAK, MIEJSCEWGARAZUPODZIEMNYM, MIEJSCENAZIEMNE
    }
}
