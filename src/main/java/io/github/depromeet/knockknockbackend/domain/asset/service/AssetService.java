package io.github.depromeet.knockknockbackend.domain.asset.service;

import io.github.depromeet.knockknockbackend.domain.group.domain.BackgroundImage;
import io.github.depromeet.knockknockbackend.domain.group.domain.Thumbnail;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.BackGroundImageRepository;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.ThumbnailRepository;
import io.github.depromeet.knockknockbackend.domain.group.exception.BackgroundImageNotFoundException;
import io.github.depromeet.knockknockbackend.domain.group.exception.ThumbNailImageNotFoundException;
import io.github.depromeet.knockknockbackend.domain.asset.presentation.dto.response.BackgroundImageDto;
import io.github.depromeet.knockknockbackend.domain.asset.presentation.dto.response.BackgroundListResponse;
import io.github.depromeet.knockknockbackend.domain.asset.presentation.dto.response.ThumbnailImageDto;
import io.github.depromeet.knockknockbackend.domain.asset.presentation.dto.response.ThumbnailListResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final BackGroundImageRepository backGroundImageRepository;
    private final ThumbnailRepository thumbnailRepository;

    public String getRandomBackgroundImageUrl() {
        Optional<BackgroundImage> randomBackground = backGroundImageRepository.findRandomBackgroundImage();
        // 디비에 썸네일 값이 안채워져있을 때임... 테스트 환경외엔 발생할수 없음!
        BackgroundImage backgroundImage = randomBackground.orElseThrow(()-> BackgroundImageNotFoundException.EXCEPTION);
        return backgroundImage.getBackgroundImageUrl();
    }

    public BackgroundListResponse getAllBackgroundImage(){
        List<BackgroundImageDto> backgroundImageDtos = backGroundImageRepository.findAll()
            .stream()
            .map(BackgroundImageDto::new)
            .collect(Collectors.toList());
        return new BackgroundListResponse(backgroundImageDtos);
    }

    public String getRandomThumbnailUrl() {
        Optional<Thumbnail> randomThumbnail = thumbnailRepository.findRandomThumbnail();
        // 디비에 썸네일 값이 안채워져있을 때임... 테스트 환경외엔 발생할수 없음!
        Thumbnail thumbnail = randomThumbnail.orElseThrow(()-> ThumbNailImageNotFoundException.EXCEPTION);
        return thumbnail.getThumbnailImageUrl();
    }

    public ThumbnailListResponse getAllThumbnailImage(){
        List<ThumbnailImageDto> thumbnailImageDtos = thumbnailRepository.findAll()
            .stream()
            .map(ThumbnailImageDto::new)
            .collect(Collectors.toList());
        return new ThumbnailListResponse(thumbnailImageDtos);
    }
}