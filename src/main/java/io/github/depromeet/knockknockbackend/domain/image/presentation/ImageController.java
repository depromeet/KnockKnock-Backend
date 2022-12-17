package io.github.depromeet.knockknockbackend.domain.image.presentation;


import io.github.depromeet.knockknockbackend.domain.image.presentation.dto.response.UploadImageResponse;
import io.github.depromeet.knockknockbackend.domain.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/images")
@RestController
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public UploadImageResponse uploadImage(@RequestPart MultipartFile file) {
        return imageService.uploadImage(file);
    }
}
