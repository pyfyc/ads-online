package com.skypro.adsonline.service;

import com.skypro.adsonline.dto.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    boolean setPassword(String currentPassword, String newPassword);
    boolean getUser();
    boolean updateUser(User user);
    boolean updateUserImage(MultipartFile image);
}
