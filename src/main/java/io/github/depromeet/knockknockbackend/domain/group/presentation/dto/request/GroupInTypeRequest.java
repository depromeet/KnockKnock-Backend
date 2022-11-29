package io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GroupInTypeRequest {

    ALL("ALL"),
    FRIEND("FRIEND"),
    OPEN("OPEN");


    private String value;

//    @JsonCreator
//    public static GroupInTypeRequest from(String value) {
//        for (GroupInTypeRequest status : GroupInTypeRequest.values()) {
//            if (status.getValue().equals(value)) {
//                return status;
//            }
//        }
//        return null;
//    }
//
//    @JsonValue
//    public String getValue() {
//        return value;
//    }
}
