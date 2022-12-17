package io.github.depromeet.knockknockbackend.domain.asset.presentation.dto.response;


import lombok.Getter;

@Getter
public class AppVersionResponse {

    private final String version;

    public AppVersionResponse(String version) {
        this.version = version;
    }
}
