package org.jobjar.jobjarapi.persistance.mappers;

import lombok.experimental.ExtensionMethod;
import org.jobjar.jobjarapi.domain.models.entities.Offer;
import org.jobjar.jobjarapi.domain.models.responses.JustJoinItResponse;

@ExtensionMethod({JustJoinItResponse.JustJoinItJob.class})
public final class JustJoinItMapper {
    public static Offer toOffer(JustJoinItResponse.JustJoinItJob job) {
        return new Offer(
                job.getGuid(),
                job.getTitle(),
                job.getSlug(),
                1000f,
                2000f,
                job.getPublishedAt(),
                job.getExpiredAt()
        );
    }
}
