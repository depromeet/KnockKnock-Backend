package io.github.depromeet.knockknockbackend.domain.report.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportReason {
    SPAM("SPAM"){
        @Override
        public String getReason() {
            return "스팸 및 도배 내용";
        }
    },
    ADVERTISING("ADVERTISING"){
        @Override
        public String getReason() {
            return "광고 및 홍보 내용";
        }
    },
    SEXUAL("SEXUAL"){
        @Override
        public String getReason() {
            return "음란물 또는 성적인 내용";
        }
    },
    PRIVACY("PRIVACY"){
        @Override
        public String getReason() {
            return "개인정보 노출";
        }
    },
    OTHER("OTHER") {
        @Override
        public String getReason() {
            return null;
        }
    };

    private final String value;

    public abstract String getReason();
}

