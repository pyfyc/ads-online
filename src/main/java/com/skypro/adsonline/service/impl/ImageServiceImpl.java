package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.dto.User;
import com.skypro.adsonline.enums.ImageType;
import com.skypro.adsonline.model.ImageEntity;
import com.skypro.adsonline.model.UserEntity;
import com.skypro.adsonline.repository.ImageRepository;
import com.skypro.adsonline.repository.UserRepository;
import com.skypro.adsonline.service.ImageService;
import com.skypro.adsonline.service.UserService;
import com.skypro.adsonline.utils.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final UserMapper userMapper;

    @Value("${users.avatar.dir.path}")
    private String avatarsDir;

    public ImageServiceImpl(UserService userService, UserRepository userRepository, ImageRepository imageRepository, UserMapper userMapper) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User updateUserImage(MultipartFile image, UserDetails currentUser) throws IOException {
        UserEntity user = userService.checkUserByUsername(currentUser.getUsername());

        Path filePath = Path.of(avatarsDir, user.getId() + "." + getExtension(image.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = image.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);) {
            bis.transferTo(bos);
        }

        ImageEntity imageDetails = new ImageEntity();
        imageDetails.setImageType(ImageType.AVATAR);
        imageDetails.setFilePath(filePath.toString());
        imageDetails.setFileExtension(getExtension(image.getOriginalFilename()));
        imageDetails.setFileSize(image.getSize());
        imageDetails.setMediaType(image.getContentType());
        imageDetails.setUser(user);
        imageRepository.save(imageDetails);

        user.setImage("/" + avatarsDir + "/" + user.getId());
        userRepository.save(user);
        return userMapper.mapToUserDto(user);
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
