package io.github.depromeet.knockknockbackend.domain.notification.domain;

import javax.persistence.AttributeConverter;

public class AlarmTypeConverter implements AttributeConverter<AlarmType, Integer> {


    @Override
    public Integer convertToDatabaseColumn(AlarmType attribute) {
        return attribute.getDbData();
    }

    @Override
    public AlarmType convertToEntityAttribute(Integer dbData) {
        return AlarmType.ofDbData(dbData);
    }
}
