package com.aplication.homeFinder.offer.service;

import com.aplication.homeFinder.offer.repository.OfferRepository;
import com.aplication.homeFinder.offer.model.Offer;
import com.aplication.homeFinder.offer.model.OfferDetails;
import com.aplication.homeFinder.offer.service.dto.OfferDto;
import com.aplication.homeFinder.offer.service.dto.OfferRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final EntityManager em;
    private final Mapper mapper = new Mapper();

    public List<OfferDto> findOffers(String kindOfProperty, Double minPrice, Double maxPrice, String location,
                                     Integer minNumberOfRooms, Integer maxNumberOfRooms, Double minArea, Double maxArea,
                                     Double minPricePerMeter, Double maxPricePerMeter, Integer minFloor, Integer maxFloor,
                                     String ownerShipForm, String finishLevel, String parkingPlace, String heating,
                                     String market, String announcerType, Integer minYearOfConstruction,
                                     Integer maxYearOfConstruction, String buildingType) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Offer> cr = cb.createQuery(Offer.class);
        Root<Offer> root = cr.from(Offer.class);

        Join<Offer, OfferDetails> offerOfferDetailsJoin = root.join("offerDetails", JoinType.INNER);

        ArrayList<Predicate> predicates = new ArrayList<>();

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
        if (location != null && !location.isEmpty()) {
            predicates.add(cb.equal(root.get("location"), location));
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
            predicates.add(cb.lessThanOrEqualTo(root.get("floor"), maxPricePerMeter));
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
            predicates.add(cb.equal(offerOfferDetailsJoin.get("additionalInformation").get("marker"), market));
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
                || "DOMWOLNOSTOJACY".equals(buildingType) ||"SZEREGOWIEC".equals(buildingType)
                || "APARTAMENTOWIEC".equals(buildingType)) {
                predicates.add(cb.equal(offerOfferDetailsJoin.get("additionalInformation")
                        .get("buildingType"), buildingType));
        }
        cr.where(cb.and(predicates.toArray(new Predicate[0])));

        List<Offer> offers = em.createQuery(cr).getResultList();
        return offers.stream()
                .map(mapper::mapOfferDto)
                .collect(Collectors.toList());
    }

    public OfferDto findOfferWithDetails(Long id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "nie znaleziono oferty"));
        OfferDetails offerDetails = offer.getOfferDetails();
        if (offerDetails == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "nie znaleziono szczegolow oferty");
        }
        return mapper.mapOfferWithDetailsDto(offer, offerDetails);
    }

    public Offer saveOffer(OfferRequest offerRequest) {
        return offerRepository.save(mapper.mapOffer(offerRequest, null));
    }

    public Offer updateOffer(OfferRequest offerRequest, Long id) {
        offerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "nie znaleziono oferty"));
        return offerRepository.save(mapper.mapOffer(offerRequest, id));
    }

    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }

}
