package com.skypro.adsonline.service;

import com.skypro.adsonline.model.AdEntity;
import com.skypro.adsonline.model.ImageInterface;
import com.skypro.adsonline.model.UserEntity;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface ImageService {
    void getImageFromDisk(HttpServletResponse response, Path filePath, ImageInterface imageDetails) throws IOException;
    void updateUserImageDetails(UserEntity user, MultipartFile image, Path filePath);
    void updateAdImageDetails(AdEntity ad, MultipartFile image, Path filePath);
    String getExtension(String fileName);
    void saveFileOnDisk(MultipartFile image, Path filePath) throws IOException;
}
