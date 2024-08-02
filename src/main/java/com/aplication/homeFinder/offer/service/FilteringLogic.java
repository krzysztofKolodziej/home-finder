package com.aplication.homeFinder.offer.service;

import com.aplication.homeFinder.offer.model.Offer;
import com.aplication.homeFinder.offer.model.OfferDetails;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class FilteringLogic {

    private final EntityManager em;
    public List<Offer> filteringMethod(FilteringSchema filteringSchema) {
        String kindOfProperty = filteringSchema.getKindOfProperty();
        Double minPrice = filteringSchema.getMinPrice();
        Double maxPrice = filteringSchema.getMaxPrice();
        String city = filteringSchema.getCity();
        Integer minNumberOfRooms = filteringSchema.getMinNumberOfRooms();
        Integer maxNumberOfRooms = filteringSchema.getMaxNumberOfRooms();
        Double minArea = filteringSchema.getMinArea();
        Double maxArea = filteringSchema.getMaxArea();
        Double minPricePerMeter = filteringSchema.getMinPricePerMeter();
        Double maxPricePerMeter = filteringSchema.getMaxPricePerMeter();
        Integer minFloor = filteringSchema.getMinFloor();
        Integer maxFloor = filteringSchema.getMaxFloor();
        String ownerShipForm = filteringSchema.getOwnerShipForm();
        String finishLevel = filteringSchema.getFinishLevel();
        String parkingPlace = filteringSchema.getParkingPlace();
        String heating = filteringSchema.getHeating();
        String market = filteringSchema.getMarket();
        String announcerType = filteringSchema.getAnnouncerType();
        Integer minYearOfConstruction = filteringSchema.getMinYearOfConstruction();
        Integer maxYearOfConstruction = filteringSchema.getMaxYearOfConstruction();
        String buildingType = filteringSchema.getBuildingType();

        ArrayList<Predicate> predicates = new ArrayList<>();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Offer> cr = cb.createQuery(Offer.class);
        Root<Offer> root = cr.from(Offer.class);

        Join<Offer, OfferDetails> offerOfferDetailsJoin = root.join("offerDetails", JoinType.INNER);

        if ("MIESZKANIE".equals(kindOfProperty) || "DOM".equals(kindOfProperty) || "LOKAL".equals(kindOfProperty)
                || "DZIALKA".equals(kindOfProperty) || "GARAZ".equals(kindOfProperty)) {
            predicates.add(cb.equal(root.get("kindOfProperty"), kindOfProperty));
        }
        if (minPrice != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        if (maxPrice != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
        }
        if (city != null && !city.isEmpty()) {
            predicates.add(cb.equal(root.get("city"), city));
        }
        if (minNumberOfRooms != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("numberOfRooms"), minNumberOfRooms));
        }
        if (maxNumberOfRooms != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("numberOfRooms"), maxNumberOfRooms));
        }
        if (minArea != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("area"), minArea));
        }
        if (maxArea != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("area"), maxArea));
        }
        if (minPricePerMeter != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("pricePerMeter"), minPricePerMeter));
        }
        if (maxPricePerMeter != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("pricePerMeter"), maxPricePerMeter));
        }
        if (minFloor != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("floor"), minFloor));
        }
        if (maxFloor != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("floor"), maxFloor));
        }
        if ("PELNAWLASNOSC".equals(ownerShipForm) || "SPOLDZIELCZE".equals(ownerShipForm) ||
                "UZYTKOWANIEWIECZYSTE".equals(ownerShipForm) || "UDZIAL".equals(ownerShipForm)) {
            predicates.add(cb.equal(offerOfferDetailsJoin.get("ownerShipForm"), ownerShipForm));
        }
        if ("DOZAMIESZKANIA".equals(finishLevel) || "DOWYKONCZENIA".equals(finishLevel) ||
                "DOREMONTU".equals(finishLevel)) {
            predicates.add(cb.equal(offerOfferDetailsJoin.get("finishLevel"), finishLevel));
        }
        if ("BRAK".equals(parkingPlace) || "MIEJSCEWGARAZUPODZIEMNYM".equals(parkingPlace) ||
                "MIEJSCENAZIEMNE".equals(parkingPlace)) {
            predicates.add(cb.equal(offerOfferDetailsJoin.get("parkingPlace"), parkingPlace));
        }
        if ("MIEJSKIE".equals(heating) || "GAZOWE".equals(heating) || "ELEKTRYCZNE".equals(heating)
                || "KOTLOWE".equals(heating) || "INNE".equals(heating)) {
            predicates.add(cb.equal(offerOfferDetailsJoin.get("heating"), heating));
        }
        if ("PIERWOTNY".equals(market) || "WTORNY".equals(market)) {
            predicates.add(cb.equal(offerOfferDetailsJoin.get("additionalInformation").get("market"), market));
        }
        if ("DEWELOPER".equals(announcerType) || "BIURONIERUCHOMOSCI".equals(announcerType)
                || "OSOBAPRYWATNA".equals(announcerType)) {
            predicates.add(cb.equal(offerOfferDetailsJoin.get("additionalInformation")
                    .get("announcerType"), announcerType));
        }
        if (minYearOfConstruction != null) {
            predicates.add(cb.greaterThanOrEqualTo(offerOfferDetailsJoin
                    .get("additionalInformation")
                    .get("yearOfConstruction"), minYearOfConstruction));
        }
        if (maxYearOfConstruction != null) {
            predicates.add(cb.lessThanOrEqualTo(offerOfferDetailsJoin
                    .get("additionalInformation")
                    .get("yearOfConstruction"), maxYearOfConstruction));
        }

        if ("BLOK".equals(buildingType) || "KAMIENICA".equals(buildingType)
                || "DOMWOLNOSTOJACY".equals(buildingType) || "SZEREGOWIEC".equals(buildingType)
                || "APARTAMENTOWIEC".equals(buildingType)) {
            predicates.add(cb.equal(offerOfferDetailsJoin.get("additionalInformation")
                    .get("buildingType"), buildingType));
        }
        cr.where(cb.and(predicates.toArray(new Predicate[0])));

        return em.createQuery(cr).getResultList();
    }

}
