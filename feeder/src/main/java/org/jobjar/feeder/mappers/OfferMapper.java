package org.jobjar.feeder.mappers;

import lombok.SneakyThrows;
import org.jobjar.feeder.models.enums.HttpClientName;
import org.jobjar.feeder.models.generics.OfferCreateDto;
import org.jobjar.feeder.models.responses.JustJoinItResponse;
import org.jobjar.feeder.models.responses.TheProtocolResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;

public class OfferMapper {
    private static final SimpleDateFormat theProtocolDateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS", Locale.ENGLISH);
    public static OfferCreateDto toOfferCreateDto(JustJoinItResponse.JustJoinItJob job) {
        var employmentType = job.getEmploymentTypes().stream().findFirst().orElse(new JustJoinItResponse.EmploymentType());
        return new OfferCreateDto(
                job.getGuid(),
                job.getTitle(),
                job.getSlug(),
                job.getCompanyName(),
                job.getWorkplaceType(),
                job.getExperienceLevel(),
                HttpClientName.JUST_JOIN_IT.name(),
                employmentType.getFrom(),
                employmentType.getTo(),
                job.getPublishedAt(),
                job.getExpiredAt(),
                new HashSet<>(job.getRequiredSkills())
        );
    }

    @SneakyThrows
    public static OfferCreateDto toOfferCreateDto(TheProtocolResponse.TheProtocolOffer job) {
        return new OfferCreateDto(
                job.getId(),
                job.getTitle(),
                job.getOfferUrlName(),
                job.getEmployer(),
                Optional.ofNullable(job.getWorkModes().get(0)).orElse("UNKNOWN"),
                Optional.ofNullable(job.getPositionLevels().get(0).getValue()).orElse("UNKNOWN"),
                HttpClientName.THE_PROTOCOL.name(),
                Optional.ofNullable(job.getSalary())
                        .map(TheProtocolResponse.TheProtocolOffer.Salary::getFrom)
                        .orElse(0d)
                        .floatValue(),
                Optional.ofNullable(job.getSalary())
                        .map(TheProtocolResponse.TheProtocolOffer.Salary::getTo)
                        .orElse(0d)
                        .floatValue(),
                job.getPublicationDateUtc(),
                null,
                new HashSet<>(job.getTechnologies())
        );
    }
}
