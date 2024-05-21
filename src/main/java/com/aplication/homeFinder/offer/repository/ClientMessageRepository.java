package com.aplication.homeFinder.offer.repository;

import com.aplication.homeFinder.offer.model.ClientMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.management.LockInfo;
import java.util.List;

public interface ClientMessageRepository extends JpaRepository<ClientMessage, Long> {
    List<ClientMessage> findAllByOrderByOffer_Id();
}
