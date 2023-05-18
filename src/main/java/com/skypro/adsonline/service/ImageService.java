package com.skypro.adsonline.service;

import com.skypro.adsonline.dto.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    User updateUserImage(MultipartFile image, UserDetails currentUser) throws IOException;
}
