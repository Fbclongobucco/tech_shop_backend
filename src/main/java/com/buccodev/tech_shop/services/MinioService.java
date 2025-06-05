package com.buccodev.tech_shop.services;

import com.buccodev.tech_shop.exceptions.FileUploadException;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class MinioService {

    private final MinioClient minioClient;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public String uploadPhoto(MultipartFile photo) {

        var fileName = UUID.randomUUID().toString();
        try {
            var file = photo.getInputStream();
            minioClient.putObject(
                   PutObjectArgs.builder()
                           .bucket("tech-shop-products")
                           .object(fileName)
                           .stream(file, photo.getSize(), -1)
                           .contentType("image/png")
                           .build()
            );
            return "http://localhost:9000/tech-shop-products/" + fileName;
        } catch (Exception e) {
            throw new FileUploadException("Error while uploading file");
        }

    }

    public void deletePhoto(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            return;
        }

        try {

            String objectName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);

            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket("tech-shop-products")
                            .object(objectName)
                            .build()
            );
        } catch (MinioException e) {
            throw new FileUploadException("Error while deleting file from MinIO: " + e.getMessage());
        } catch (Exception e) {
            throw new FileUploadException("Error processing file deletion: " + e.getMessage());
        }
    }

}
