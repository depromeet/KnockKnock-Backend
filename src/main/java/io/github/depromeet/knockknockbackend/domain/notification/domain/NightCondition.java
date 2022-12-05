package io.github.depromeet.knockknockbackend.domain.notification.domain;

public enum NightCondition {
    START_TIME(21),
    END_TIME(8);

    private final int hour;

    NightCondition(int hour) {
        this.hour = hour;
    }

    public int getHour() {
        return hour;
    }
}
