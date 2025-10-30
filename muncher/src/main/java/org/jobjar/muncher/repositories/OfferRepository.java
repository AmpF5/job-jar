package org.jobjar.muncher.repositories;

import org.jobjar.muncher.models.entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID> {
}
