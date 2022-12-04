package io.github.depromeet.knockknockbackend.domain.group.service;

import io.github.depromeet.knockknockbackend.domain.group.domain.Thumbnail;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.ThumbnailRepository;
import io.github.depromeet.knockknockbackend.domain.group.exception.ThumbNailImageNotFoundException;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.ThumbnailImageDto;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.ThumbnailListResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThumbnailImageService {


    private final ThumbnailRepository thumbnailRepository;

    public String getRandomThumbnailUrl() {
        Optional<Thumbnail> randomThumbnail = thumbnailRepository.findRandomThumbnail();
        // 디비에 썸네일 값이 안채워져있을 때임... 테스트 환경외엔 발생할수 없음!
        Thumbnail thumbnail = randomThumbnail.orElseThrow(()-> ThumbNailImageNotFoundException.EXCEPTION);
        return thumbnail.getThumbnailImageUrl();
    }


    public ThumbnailListResponse getAllBackgroundImage(){

        List<Thumbnail> thumbnailImages = thumbnailRepository.findAll();

        List<ThumbnailImageDto> thumbnailImageDtoList = thumbnailImages.stream().map(
                ThumbnailImageDto::new)
            .collect(Collectors.toList());
        return new ThumbnailListResponse(thumbnailImageDtoList);
    }
}
