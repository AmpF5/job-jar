package org.jobjar.jobjarapi.infrastructure.mappers;

import lombok.experimental.ExtensionMethod;
import org.jobjar.jobjarapi.domain.Offer;
import org.jobjar.jobjarapi.domain.responses.JustJoinItResponse;

@ExtensionMethod({JustJoinItResponse.JustJoinItJob.class})
public final class JustJoinItMapper {
    public static Offer toOffer(JustJoinItResponse.JustJoinItJob job) {
        var offer = new Offer(
                job.getGuid(),
                job.getTitle(),
                job.getSlug(),
                1000f,
                2000f,
                job.getPublishedAt(),
                job.getExpiredAt()
        );
        return offer;
    }
}
