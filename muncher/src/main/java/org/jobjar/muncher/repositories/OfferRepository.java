package org.jobjar.muncher.repositories;

import org.jobjar.muncher.models.entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID> {
    @Query(value = "SELECT * FROM offers where external_id in :externalIds", nativeQuery = true)
    List<Offer> findByExternalIds(Set<UUID> externalIds);
}
