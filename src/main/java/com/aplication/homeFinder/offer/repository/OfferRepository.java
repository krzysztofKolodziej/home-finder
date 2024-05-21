package com.aplication.homeFinder.offer.repository;

import com.aplication.homeFinder.offer.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {
}
