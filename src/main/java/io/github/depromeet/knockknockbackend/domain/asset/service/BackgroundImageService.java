package io.github.depromeet.knockknockbackend.domain.group.service;

import io.github.depromeet.knockknockbackend.domain.group.domain.BackgroundImage;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.BackGroundImageRepository;
import io.github.depromeet.knockknockbackend.domain.group.exception.BackgroundImageNotFoundException;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.BackgroundImageDto;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.BackgroundListResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackgroundImageService {


    private final BackGroundImageRepository backGroundImageRepository;

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
}
