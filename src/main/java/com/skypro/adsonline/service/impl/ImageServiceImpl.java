package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.model.*;
import com.skypro.adsonline.repository.AdImageRepository;
import com.skypro.adsonline.repository.AvatarRepository;
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
    private final AdImageRepository adImageRepository;
    private final AvatarRepository avatarRepository;

    public ImageServiceImpl(AdImageRepository adImageRepository, AvatarRepository avatarRepository) {
        this.adImageRepository = adImageRepository;
        this.avatarRepository = avatarRepository;
    }

    @Override
    public void getImageFromDisk(HttpServletResponse response, Path filePath, ImageInterface imageDetails) throws IOException {
        try (InputStream is = Files.newInputStream(filePath);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(imageDetails.getMediaType());
            response.setContentLength((int) imageDetails.getFileSize());
            is.transferTo(os);
        }
    }

    /**
     * Update user avatar image details.
     * @param user user whose avatar to be updated
     * @param image avatar's image
     * @param filePath avatar's file path on disk (where it is to be saved)
     */
    @Override
    public void updateUserImageDetails(UserEntity user, MultipartFile image, Path filePath) {
        AvatarEntity imageDetails = avatarRepository.findByUser(user).orElse(new AvatarEntity());
        imageDetails.setFilePath(filePath.toString());
        imageDetails.setFileExtension(getExtension(image.getOriginalFilename()));
        imageDetails.setFileSize(image.getSize());
        imageDetails.setMediaType(image.getContentType());
        imageDetails.setUser(user);
        avatarRepository.save(imageDetails);
    }

    @Override
    public String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * Update ad image details.
     * @param ad ad that is to be updated
     * @param image ad's image
     * @param filePath ad's image file path on disk (where it is to be saved)
     */
    @Override
    public void updateAdImageDetails(AdEntity ad, MultipartFile image, Path filePath) {
        AdImageEntity imageDetails = adImageRepository.findByAd(ad).orElse(new AdImageEntity());
        imageDetails.setFilePath(filePath.toString());
        imageDetails.setFileExtension(getExtension(image.getOriginalFilename()));
        imageDetails.setFileSize(image.getSize());
        imageDetails.setMediaType(image.getContentType());
        imageDetails.setAd(ad);
        adImageRepository.save(imageDetails);
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
