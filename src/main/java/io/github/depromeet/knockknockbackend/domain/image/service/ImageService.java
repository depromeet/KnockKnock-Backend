package io.github.depromeet.knockknockbackend.domain.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import io.github.depromeet.knockknockbackend.domain.image.exception.BadFileExtensionException;
import io.github.depromeet.knockknockbackend.domain.image.exception.FileEmptyException;
import io.github.depromeet.knockknockbackend.domain.image.exception.FileUploadFailException;
import io.github.depromeet.knockknockbackend.domain.image.presentation.dto.response.UploadImageResponse;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.s3.base_image_url}")
    private String baseUrl;

    private final AmazonS3 amazonS3;

    public UploadImageResponse uploadImage(MultipartFile file) {
        String url = upload(file);
        return new UploadImageResponse(url);
    }

    public String upload(MultipartFile file) {
        if(file.isEmpty() && file.getOriginalFilename() != null) throw FileEmptyException.EXCEPTION;
        String originalFilename = file.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        if (!(ext.equals("jpg") || ext.equals("HEIC") || ext.equals("jpeg") || ext.equals("png") || ext.equals("heic"))) {
            throw BadFileExtensionException.EXCEPTION;
        }
        
        String randomName = UUID.randomUUID().toString();

        String fileName = SecurityUtils.getCurrentUserId() + "|" + randomName + "." + ext;

        try {
            ObjectMetadata objMeta = new ObjectMetadata();
            byte[] bytes = IOUtils.toByteArray(file.getInputStream());
            objMeta.setContentLength(bytes.length);
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), objMeta)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw FileUploadFailException.EXCEPTION;
        }

        return baseUrl + "/" + fileName;
    }

    public void delete(String objectName) {
        amazonS3.deleteObject(bucket, objectName);
    }

}
