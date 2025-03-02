package com.aplication.homeFinder.offer.service;

import com.aplication.homeFinder.offer.model.Offer;
import com.aplication.homeFinder.offer.model.offerdetail.OfferDetails;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Component
public class FilteringLogic {

    private final EntityManager em;

    public List<Offer> filteringMethod(FilteringSchema filteringSchema) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Offer> cr = cb.createQuery(Offer.class);
        Root<Offer> root = cr.from(Offer.class);
        Join<Offer, OfferDetails> offerOfferDetailsJoin = root.join("offerDetails", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();

        addKindOfPropertyPredicate(
                predicates, cb, root, filteringSchema.getKindOfProperty()
        );
        addPricePredicates(
                predicates, cb, root, filteringSchema.getMinPrice(), filteringSchema.getMaxPrice()
        );
        addCityPredicate(
                predicates, cb, root, filteringSchema.getCity()
        );
        addNumberOfRoomsPredicates(
                predicates, cb, root, filteringSchema.getMinNumberOfRooms(), filteringSchema.getMaxNumberOfRooms()
        );
        addAreaPredicates(
                predicates, cb, root, filteringSchema.getMinArea(), filteringSchema.getMaxArea()
        );
        addPricePerMeterPredicates(
                predicates, cb, root, filteringSchema.getMinPricePerMeter(), filteringSchema.getMaxPricePerMeter()
        );
        addFloorPredicates(
                predicates, cb, root, filteringSchema.getMinFloor(), filteringSchema.getMaxFloor()
        );
        addOwnershipFormPredicate(
                predicates, cb, offerOfferDetailsJoin, filteringSchema.getOwnerShipForm()
        );
        addFinishLevelPredicate(
                predicates, cb, offerOfferDetailsJoin, filteringSchema.getFinishLevel()
        );
        addParkingPlacePredicate(
                predicates, cb, offerOfferDetailsJoin, filteringSchema.getParkingPlace()
        );
        addHeatingPredicate(
                predicates, cb, offerOfferDetailsJoin, filteringSchema.getHeating()
        );
        addMarketPredicate(
                predicates, cb, offerOfferDetailsJoin, filteringSchema.getMarket()
        );
        addAnnouncerTypePredicate(
                predicates, cb, offerOfferDetailsJoin, filteringSchema.getAnnouncerType()
        );
        addYearOfConstructionPredicates(
                predicates, cb, offerOfferDetailsJoin, filteringSchema.getMinYearOfConstruction(),
                filteringSchema.getMaxYearOfConstruction()
        );
        addBuildingTypePredicate(
                predicates, cb, offerOfferDetailsJoin, filteringSchema.getBuildingType()
        );

        cr.where(cb.and(predicates.toArray(new Predicate[0])));
        return em.createQuery(cr).getResultList();
    }

    private void addKindOfPropertyPredicate(List<Predicate> predicates, CriteriaBuilder cb, Root<Offer> root, String kindOfProperty) {
        if (kindOfProperty != null && Arrays.asList("MIESZKANIE", "DOM", "LOKAL", "DZIALKA", "GARAZ")
                .contains(kindOfProperty)) {
            predicates.add(cb.equal(root.get("kindOfProperty"), kindOfProperty));}
    }

    private void addPricePredicates(
            List<Predicate> predicates, CriteriaBuilder cb, Root<Offer> root, Double minPrice, Double maxPrice) {
        if (minPrice != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        if (maxPrice != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
        }
    }

    private void addCityPredicate(List<Predicate> predicates, CriteriaBuilder cb, Root<Offer> root, String city) {
        if (city != null && !city.isEmpty()) {
            predicates.add(cb.equal(root.get("city"), city));
        }
    }

    private void addNumberOfRoomsPredicates(
            List<Predicate> predicates, CriteriaBuilder cb, Root<Offer> root, Integer minNumberOfRooms,
            Integer maxNumberOfRooms) {
        if (minNumberOfRooms != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("numberOfRooms"), minNumberOfRooms));
        }
        if (maxNumberOfRooms != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("numberOfRooms"), maxNumberOfRooms));
        }
    }

    private void addAreaPredicates(
            List<Predicate> predicates, CriteriaBuilder cb, Root<Offer> root, Double minArea, Double maxArea) {
        if (minArea != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("area"), minArea));
        }
        if (maxArea != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("area"), maxArea));
        }
    }

    private void addPricePerMeterPredicates(
            List<Predicate> predicates, CriteriaBuilder cb, Root<Offer> root, Double minPricePerMeter,
            Double maxPricePerMeter) {
        if (minPricePerMeter != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("pricePerMeter"), minPricePerMeter));
        }
        if (maxPricePerMeter != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("pricePerMeter"), maxPricePerMeter));
        }
    }

    private void addFloorPredicates(
            List<Predicate> predicates, CriteriaBuilder cb, Root<Offer> root, Integer minFloor, Integer maxFloor) {
        if (minFloor != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("floor"), minFloor));
        }
        if (maxFloor != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("floor"), maxFloor));
        }
    }

    private void addOwnershipFormPredicate(
            List<Predicate> predicates, CriteriaBuilder cb, Join<Offer, OfferDetails> join, String ownerShipForm) {
        if (ownerShipForm != null && Arrays.asList("PELNAWLASNOSC", "SPOLDZIELCZE", "UZYTKOWANIEWIECZYSTE", "UDZIAL")
                .contains(ownerShipForm)) {
            predicates.add(cb.equal(join.get("ownerShipForm"), ownerShipForm));
        }
    }

    private void addFinishLevelPredicate(
            List<Predicate> predicates, CriteriaBuilder cb, Join<Offer, OfferDetails> join, String finishLevel) {
        if (finishLevel != null && Arrays.asList("DOZAMIESZKANIA", "DOWYKONCZENIA", "DOREMONTU").contains(finishLevel)) {
            predicates.add(cb.equal(join.get("finishLevel"), finishLevel));
        }
    }

    private void addParkingPlacePredicate(
            List<Predicate> predicates, CriteriaBuilder cb, Join<Offer, OfferDetails> join, String parkingPlace) {
        if (parkingPlace != null && Arrays.asList("BRAK", "MIEJSCEWGARAZUPODZIEMNYM", "MIEJSCENAZIEMNE")
                .contains(parkingPlace)) {
            predicates.add(cb.equal(join.get("parkingPlace"), parkingPlace));
        }
    }

    private void addHeatingPredicate(
            List<Predicate> predicates, CriteriaBuilder cb, Join<Offer, OfferDetails> join, String heating) {
        if (heating != null && Arrays.asList("MIEJSKIE", "GAZOWE", "ELEKTRYCZNE", "KOTLOWE", "INNE").contains(heating)) {
            predicates.add(cb.equal(join.get("heating"), heating));
        }
    }

    private void addMarketPredicate(
            List<Predicate> predicates, CriteriaBuilder cb, Join<Offer, OfferDetails> join, String market) {
        if (market != null && Arrays.asList("PIERWOTNY", "WTORNY").contains(market)) {
            predicates.add(cb.equal(join.get("additionalInformation").get("market"), market));
        }
    }

    private void addAnnouncerTypePredicate(
            List<Predicate> predicates, CriteriaBuilder cb, Join<Offer, OfferDetails> join, String announcerType) {
        if (announcerType != null && Arrays.asList("DEWELOPER", "BIURONIERUCHOMOSCI", "OSOBAPRYWATNA")
                .contains(announcerType)) {
            predicates.add(cb.equal(join.get("additionalInformation").get("announcerType"), announcerType));
        }
    }

    private void addYearOfConstructionPredicates(
            List<Predicate> predicates, CriteriaBuilder cb, Join<Offer, OfferDetails> join,
            Integer minYearOfConstruction, Integer maxYearOfConstruction) {
        if (minYearOfConstruction != null) {
            predicates.add(cb.greaterThanOrEqualTo(join.get("additionalInformation").get("yearOfConstruction"),
                    minYearOfConstruction));
        }
        if (maxYearOfConstruction != null) {
            predicates.add(cb.lessThanOrEqualTo(join.get("additionalInformation").get("yearOfConstruction"),
                    maxYearOfConstruction));
        }
    }

    private void addBuildingTypePredicate(
            List<Predicate> predicates, CriteriaBuilder cb, Join<Offer, OfferDetails> join, String buildingType) {
        if (buildingType != null && Arrays.asList("BLOK", "KAMIENICA", "DOMWOLNOSTOJACY", "SZEREGOWIEC", "APARTAMENTOWIEC")
                .contains(buildingType)) {
            predicates.add(cb.equal(join.get("additionalInformation").get("buildingType"), buildingType));
        }
    }

}
