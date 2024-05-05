package com.aplication.homeFinder.offer;

import com.aplication.homeFinder.offer.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {

}
