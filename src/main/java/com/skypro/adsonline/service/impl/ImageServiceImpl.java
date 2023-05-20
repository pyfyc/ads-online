package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.enums.ImageType;
import com.skypro.adsonline.model.AdEntity;
import com.skypro.adsonline.model.ImageEntity;
import com.skypro.adsonline.model.UserEntity;
import com.skypro.adsonline.repository.ImageRepository;
import com.skypro.adsonline.service.ImageService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public void getImageFromDisk(HttpServletResponse response, Path filePath, ImageEntity imageDetails) throws IOException {
        try (InputStream is = Files.newInputStream(filePath);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(imageDetails.getMediaType());
            response.setContentLength((int) imageDetails.getFileSize());
            is.transferTo(os);
        }
    }

    @Override
    public void updateUserImageDetails(UserEntity user, MultipartFile image, Path filePath) {
        ImageEntity imageDetails = imageRepository.findByUser(user).orElse(new ImageEntity());
        imageDetails.setImageType(ImageType.AVATAR);
        imageDetails.setFilePath(filePath.toString());
        imageDetails.setFileExtension(getExtension(image.getOriginalFilename()));
        imageDetails.setFileSize(image.getSize());
        imageDetails.setMediaType(image.getContentType());
        imageDetails.setUser(user);
        imageRepository.save(imageDetails);
    }

    @Override
    public String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    @Override
    public void updateAdImageDetails(AdEntity ad, MultipartFile image, Path filePath) {
        ImageEntity imageDetails = imageRepository.findByAd(ad).orElse(new ImageEntity());
        imageDetails.setImageType(ImageType.AD_IMAGE);
        imageDetails.setFilePath(filePath.toString());
        imageDetails.setFileExtension(getExtension(image.getOriginalFilename()));
        imageDetails.setFileSize(image.getSize());
        imageDetails.setMediaType(image.getContentType());
        imageDetails.setAd(ad);
        imageRepository.save(imageDetails);
    }

    @Override
    public void saveFileOnDisk(MultipartFile image, Path filePath) throws IOException {
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = image.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);) {
            bis.transferTo(bos);
        }
    }
}
