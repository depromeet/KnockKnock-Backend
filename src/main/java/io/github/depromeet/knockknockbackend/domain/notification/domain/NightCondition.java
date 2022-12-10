package io.github.depromeet.knockknockbackend.domain.notification.domain;

import java.time.LocalDateTime;

public enum NightCondition {
    START_TIME(21),
    END_TIME(8);

    private final int hour;

    NightCondition(int hour) {
        this.hour = hour;
    }

    public static boolean isNight() {
        int nowHour = LocalDateTime.now().getHour();
        return nowHour >= NightCondition.START_TIME.hour || nowHour < NightCondition.END_TIME.hour;
    }

}
