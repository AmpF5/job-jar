package org.jobjar.feeder.models.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jobjar.feeder.models.generics.IOfferResponse;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JustJoinItResponse implements IOfferResponse {
    private List<JustJoinItJob> data;

    private JustJoinItMetaData meta;

    // === Inner data types ===
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class JustJoinItJob {
        private String guid;
        private String slug;
        private String title;
        private List<String> requiredSkills;
        private List<String> niceToHaveSkills;
        private String workplaceType;
        private String workingTime;
        private String experienceLevel;
        private List<EmploymentType> employmentTypes;
        private Integer categoryId;
        private List<MultiLocation> multilocation;
        private String city;
        private String street;
        private Double latitude;
        private Double longitude;
        private Boolean remoteInterview;
        private String companyName;
        private String companyLogoThumbUrl;
        private Date publishedAt;
        private Date expiredAt;
        private Boolean openToHireUkrainians;
        private List<Language> languages;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EmploymentType {
        private Float from;
        private Float to;
        private String currency;
        private String type;
        private String unit;
        private Boolean gross;
        private Float fromChf;
        private Float fromEur;
        private Float fromGbp;
        private Float fromPln;
        private Float fromUsd;
        private Float toChf;
        private Float toEur;
        private Float toGbp;
        private Float toPln;
        private Float toUsd;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MultiLocation {
        private String city;
        private String slug;
        private String street;
        private Double latitude;
        private Double longitude;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Language {
        private String code;
        private String level;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class JustJoinItMetaData {
        private Integer page;
        private Integer totalItems;
        private Integer totalPages;
        private Integer prevPage;
        private Integer nextPage;
    }
}