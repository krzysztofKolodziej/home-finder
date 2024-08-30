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
    @Setter(AccessLevel.NONE)
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
    @Builder
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
        DEWELOPER, BIURO_NIERUCHOMOSCI, OSOBA_PRYWATNA
    }
    public enum BuildingType {
        BLOK, KAMIENICA, DOM_WOLNOSTOJACY, SZEREGOWIEC, APARTAMENTOWIEC
    }
    public enum FinishLevel {
        DO_ZAMIESZKANIA, DO_WYKONCZENIA, DO_REMONTU
    }
    public enum Heating {
        MIEJSKIE, GAZOWE, ELEKTRYCZNE, KOTLOWE, INNE
    }
    public enum Market {
        PIERWOTNY, WTORNY
    }
    public enum OwnershipForm {
        PELNA_WLASNOSC, SPOLDZIELCZE, UZYTKOWANIE_WIECZYSTE, UDZIAL
    }
    public enum ParkingPlace {
        BRAK, MIEJSCE_W_GARAZU_PODZIEMNYM, MIEJSCE_NAZIEMNE
    }
}
