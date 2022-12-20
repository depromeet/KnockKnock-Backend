package io.github.depromeet.knockknockbackend.domain.asset.presentation;


import io.github.depromeet.knockknockbackend.domain.asset.presentation.dto.response.AppVersionResponse;
import io.github.depromeet.knockknockbackend.domain.asset.presentation.dto.response.BackgroundsResponse;
import io.github.depromeet.knockknockbackend.domain.asset.presentation.dto.response.ProfileImageDto;
import io.github.depromeet.knockknockbackend.domain.asset.presentation.dto.response.ProfileImagesResponse;
import io.github.depromeet.knockknockbackend.domain.asset.presentation.dto.response.ReactionsResponse;
import io.github.depromeet.knockknockbackend.domain.asset.presentation.dto.response.ThumbnailsResponse;
import io.github.depromeet.knockknockbackend.domain.asset.service.AssetService;
import io.github.depromeet.knockknockbackend.global.annotation.DisableSecurity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/asset")
@RestController
@Tag(name = "이미지 관련 컨트롤러", description = "")
@SecurityRequirement(name = "access-token")
public class AssetController {

    private final AssetService assetService;

    @Operation(summary = "백그라운드 이미지")
    @GetMapping("/backgrounds")
    public BackgroundsResponse getBackgroundImageUrls() {
        return assetService.getAllBackgroundImage();
    }

    @Operation(summary = "썸네일 이미지")
    @GetMapping("/thumbnails")
    public ThumbnailsResponse getThumbnailImageUrls() {
        return assetService.getAllThumbnailImage();
    }

    @Operation(summary = "프로필 이미지")
    @DisableSecurity
    @GetMapping("/profiles")
    public ProfileImagesResponse getAllProfileImages() {
        return assetService.getAllProfileImages();
    }

    @Operation(summary = "프로필 이미지 랜덤하게 받기")
    @DisableSecurity
    @GetMapping("/profiles/random")
    public ProfileImageDto getRandomProfileImageUrl() {
        return assetService.getRandomProfileImageUrl();
    }

    @Operation(summary = "리액션 이미지 받기")
    @GetMapping("/reactions")
    public ReactionsResponse getAllReactionImages() {
        return assetService.getAllReactionImages();
    }

    @Operation(summary = "앱버젼 api ")
    @DisableSecurity
    @Tag(name = "앱버젼 체크")
    @GetMapping("/version")
    public AppVersionResponse getAppVersion() {
        return assetService.getAppVersion();
    }
}
