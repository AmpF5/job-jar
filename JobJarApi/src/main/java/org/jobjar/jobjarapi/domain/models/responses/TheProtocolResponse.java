package org.jobjar.jobjarapi.domain.models.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TheProtocolResponse {
    private Page page;
    private int offersCount;
    private List<TheProtocolOffer> offers;
    private Filters filters;
    private OrderBy orderBy;

    // === Inner data types ===
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Page {
        private int number;
        private int size;
        private int count;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TheProtocolOffer {
        private String id;
        private String groupId;
        private String title;
        private String employer;
        private String employerId;
        private String logoUrl;
        private String offerUrlName;
        private List<String> aboutProject;
        private List<Workplace> workplace;
        private List<PositionLevel> positionLevels;
        private List<TypeOfContract> typesOfContracts;
        private List<String> technologies;
        private boolean _new;
        private String publicationDateUtc;
        private boolean lastCall;
        private String language;
        private Salary salary;
        private List<String> workModes;
        private boolean immediateEmployment;
        private boolean isSupportingUkraine;
        private Addons addons;
        private boolean isFromExternalLocations;
        private Badges badges;
        // getters and setters

        @Data
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Workplace {
            private String location;
            private String city;
            private String region;
            // getters and setters
        }

        @Data
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class PositionLevel {
            private String value;
            // getters and setters
        }

        @Data
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class TypeOfContract {
            private int id;
            private Salary salary;
            // getters and setters
        }

        @Data
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Salary {
            private Double from;
            private Double to;
            private String currencySymbol;
            private Integer timeUnitId;
            private TimeUnit timeUnit;
            private String kindName;
            private String currency;

            @Data
            @NoArgsConstructor
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class TimeUnit {
                private String shortForm;
                private String longForm;
            }
        }

        @Data
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Addons {
            private List<String> searchableLocations;
            private List<String> searchableRegions;
            private boolean isWholePoland;
        }

        @Data
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Badges {
            private boolean _new;
            private boolean lastCall;
            private boolean immediateEmployment;
            private boolean isSupportingUkraine;
            private boolean isFromExternalLocations;
            private boolean isQuickApply;
        }
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Filters {
        private List<String> cities;
        private List<String> regionsOfWorld;
        private List<String> technologies;
        private List<String> expectedTechnologies;
        private List<String> excludedTechnologies;
        private List<String> niceToHaveTechnologies;
        private List<String> keywords;
        private List<String> specializationsCodes;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrderBy {
        private String field;
        private String direction;
    }
}