package com.buccodev.tech_shop.services;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Service
public class MinioService {

    private final MinioClient minioClient;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public String uploadPhoto(MultipartFile photo) throws IOException {

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
            throw new FileNotFoundException("error while uploading file");
        }

    }

}
