package com.aplication.homeFinder.admin;
;
import com.aplication.homeFinder.offer.model.enumClass.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private KindOfProperty kindOfProperty;
    private String title;
    private String location;
    private double price;
    private double area;
    private double pricePerMeter;
    private int numberOfRooms;
    private int floor;
    private double rent;
    @Enumerated(EnumType.STRING)
    private OwnershipForm ownershipForm;
    private String description;
    @Enumerated(EnumType.STRING)
    private BuildingType buildingType;
    @Enumerated(EnumType.STRING)
    private Heating heating;
    private int yearOfConstruction;
    @Enumerated(EnumType.STRING)
    private FinishLevel finishLevel;
    private String media;
    private String equipment;
    private String contactDetails;
}
