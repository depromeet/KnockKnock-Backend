package io.github.depromeet.knockknockbackend.domain.group.service;

import io.github.depromeet.knockknockbackend.domain.group.domain.BackgroundImage;
import io.github.depromeet.knockknockbackend.domain.group.domain.Thumbnail;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.BackGroundImageRepository;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.ThumbnailRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackgroundImageService {


    private final BackGroundImageRepository backGroundImageRepository;

    public String getBackgroundImageUrl() {
        Optional<BackgroundImage> randomBackgorond = backGroundImageRepository.findRandomBackgroundImage();
        // 디비에 썸네일 값이 안채워져있을 때임... 테스트 환경외엔 발생할수 없음!
        BackgroundImage backgroundImage = randomBackgorond.orElseGet(BackgroundImage::new);
        return backgroundImage.getBackgroundImageUrl();
    }

    public String getBackgroundImageUrl(@Nullable String backgroundImageUrl) {
        if(backgroundImageUrl == null){
            return getBackgroundImageUrl();
        }
        return backgroundImageUrl;
    }

}
