package org.jobjar.feeder.infrastructure;

import lombok.RequiredArgsConstructor;
import models.dtos.OfferCreateDto;
import models.enums.ExperienceLevel;
import models.enums.JobSite;
import models.enums.WorkplaceType;
import org.jobjar.feeder.adapters.JustJoinItAdapter;
import org.jobjar.feeder.adapters.TheProtocolAdapter;
import org.jobjar.feeder.infrastructure.services.OfferPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;


@RestController
@RequiredArgsConstructor
public class TestApi {
    private final JustJoinItAdapter justJoinItAdapter;
    private final TheProtocolAdapter theProtocolAdapter;
    private final OfferPublisher offerPublisher;
    private static final Logger log = LoggerFactory.getLogger("Fetching data job");

    @GetMapping()
    public void Test() {
//        var allOffers = new ArrayList<OfferCreateDto>();
//        log.info("Start fetching offers");
//        var start = System.nanoTime();
//
//        var jjitOffers = justJoinItAdapter.getOffers();
//        var theProtocolOffers = theProtocolAdapter.getOffers();
//
//        allOffers.addAll(jjitOffers);
//        allOffers.addAll(theProtocolOffers);
//
//        var end =  System.nanoTime();
//        log.info("Fetching offers end, {} offers found in {} ms", allOffers.size(), TimeConverter.getElapsedTime(start, end));
        var offer1 = new OfferCreateDto(
                "123e4567-e89b-12d3-a456-426614174000",
                "Java Developer1",
                "java-developer",
                "TechCorp",
                WorkplaceType.REMOTE,
                ExperienceLevel.MID,
                JobSite.JUST_JOIN_IT,
                10000.0f,
                15000.0f,
                Date.from(Instant.now()),
                Date.from(Instant.now()),
                Set.of("Java", "Spring Boot", "Docker")
        );

        var offer2 = new OfferCreateDto(
                "123e4567-e89b-12d3-a456-426614174000",
                "Java Developer2",
                "java-developer",
                "TechCorp",
                WorkplaceType.REMOTE,
                ExperienceLevel.MID,
                JobSite.JUST_JOIN_IT,
                10000.0f,
                15000.0f,
                Date.from(Instant.now()),
                Date.from(Instant.now()),
                Set.of("Java", "Spring Boot", "Docker")
        );

        var offer3 = new OfferCreateDto(
                "123e4567-e89b-12d3-a456-426614174000",
                "Java Developer3",
                "java-developer",
                "TechCorp",
                WorkplaceType.REMOTE,
                ExperienceLevel.MID,
                JobSite.JUST_JOIN_IT,
                10000.0f,
                15000.0f,
                Date.from(Instant.now()),
                Date.from(Instant.now()),
                Set.of("Java", "Spring Boot", "Docker")
        );

        var offers = List.of(offer1, offer2, offer3);

        offerPublisher.publishBatch(offers);
    }
}
