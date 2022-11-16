package io.github.depromeet.knockknockbackend.domain.group.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GroupType {


    // 홀로 외침방 ( 카카오톡 오픈 채팅 느낌 )
    OPEN("open"),

    // 친구에게 푸시보내기
    FRIEND("friend");

    private String type;

}
