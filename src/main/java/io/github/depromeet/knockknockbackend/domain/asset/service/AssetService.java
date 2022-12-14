package io.github.depromeet.knockknockbackend.domain.asset.service;

import io.github.depromeet.knockknockbackend.domain.asset.domain.BackgroundImage;
import io.github.depromeet.knockknockbackend.domain.asset.domain.ProfileImage;
import io.github.depromeet.knockknockbackend.domain.asset.domain.Thumbnail;
import io.github.depromeet.knockknockbackend.domain.asset.domain.repository.ProfileImageRepository;
import io.github.depromeet.knockknockbackend.domain.asset.exception.ProfileImageNotFoundException;
import io.github.depromeet.knockknockbackend.domain.asset.presentation.dto.response.ProfileImageDto;
import io.github.depromeet.knockknockbackend.domain.asset.presentation.dto.response.ProfileImagesResponse;
import io.github.depromeet.knockknockbackend.domain.asset.domain.repository.BackGroundImageRepository;
import io.github.depromeet.knockknockbackend.domain.asset.domain.repository.ThumbnailRepository;
import io.github.depromeet.knockknockbackend.domain.asset.exception.BackgroundImageNotFoundException;
import io.github.depromeet.knockknockbackend.domain.asset.exception.ThumbNailImageNotFoundException;
import io.github.depromeet.knockknockbackend.domain.asset.presentation.dto.response.BackgroundImageDto;
import io.github.depromeet.knockknockbackend.domain.asset.presentation.dto.response.BackgroundsResponse;
import io.github.depromeet.knockknockbackend.domain.asset.presentation.dto.response.ThumbnailImageDto;
import io.github.depromeet.knockknockbackend.domain.asset.presentation.dto.response.ThumbnailsResponse;
import io.github.depromeet.knockknockbackend.domain.group.service.AssetUtils;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssetService implements AssetUtils {

    private final BackGroundImageRepository backGroundImageRepository;
    private final ThumbnailRepository thumbnailRepository;

    private final ProfileImageRepository profileImageRepository;

    public String getRandomBackgroundImageUrl() {
        BackgroundImage randomBackground = backGroundImageRepository.findRandomBackgroundImage()
            .orElseThrow(()-> BackgroundImageNotFoundException.EXCEPTION);
        return randomBackground.getBackgroundImageUrl();
    }

    public BackgroundsResponse getAllBackgroundImage(){
        List<BackgroundImageDto> backgroundImageDtos = backGroundImageRepository.findAllOrderByRank()
            .stream()
            .map(BackgroundImageDto::new)
            .collect(Collectors.toList());
        return new BackgroundsResponse(backgroundImageDtos);
    }

    public String getRandomThumbnailUrl() {
        Thumbnail randomThumbnail = thumbnailRepository.findRandomThumbnail()
            .orElseThrow(()-> ThumbNailImageNotFoundException.EXCEPTION);
        return randomThumbnail.getThumbnailImageUrl();
    }

    public ThumbnailsResponse getAllThumbnailImage(){
        List<ThumbnailImageDto> thumbnailImageDtos = thumbnailRepository.findAllOrderByRank()
            .stream()
            .map(ThumbnailImageDto::new)
            .collect(Collectors.toList());
        return new ThumbnailsResponse(thumbnailImageDtos);
    }

    public ProfileImageDto getRandomProfileImageUrl() {
        ProfileImage randomProfileImage = profileImageRepository.findRandomProfileImage()
            .orElseThrow(()-> ProfileImageNotFoundException.EXCEPTION);
        return new ProfileImageDto(randomProfileImage);
    }

    public ProfileImagesResponse getAllProfileImages() {
        List<ProfileImageDto> profileImageDtos = profileImageRepository.findAllOrderByRank()
            .stream()
            .map(ProfileImageDto::new)
            .collect(Collectors.toList());
        return new ProfileImagesResponse(profileImageDtos);
    }
}
