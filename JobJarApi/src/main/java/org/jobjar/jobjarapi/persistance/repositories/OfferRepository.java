package org.jobjar.jobjarapi.persistance.repositories;

import org.jobjar.jobjarapi.domain.models.entities.Company;
import org.jobjar.jobjarapi.domain.models.entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
}
