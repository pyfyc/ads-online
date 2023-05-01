package com.skypro.adsonline.service;

import com.skypro.adsonline.dto.User;
import com.skypro.adsonline.security.SecurityUser;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    boolean setPassword(String currentPassword, String newPassword);
    User getUser(SecurityUser currentUser);
    boolean updateUser(User user);
    boolean updateUserImage(MultipartFile image);
}
