package io.github.depromeet.knockknockbackend.domain.notification.domain;

import java.util.Arrays;

public enum AlarmType {

    ONETOONE(0),
    ONETOGROUP(1);

    private final Integer dbData;

    AlarmType(Integer dbData) {
        this.dbData = dbData;
    }

    public static AlarmType ofDbData(Integer dbData) {
        return Arrays.stream(AlarmType.values())
            .filter(alarmType -> alarmType.isEquivalentTo(dbData))
            .findAny()
            .orElseThrow();
    }

    private boolean isEquivalentTo(Integer dbData) {
        return this.dbData.equals(dbData);
    }

    public int getDbData() {
        return dbData;
    }

}
