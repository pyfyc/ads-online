package com.skypro.adsonline.service;

import com.skypro.adsonline.dto.NewPassword;
import com.skypro.adsonline.dto.User;
import com.skypro.adsonline.model.UserEntity;
import com.skypro.adsonline.security.SecurityUser;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    boolean setPassword(String currentPassword, String newPassword);
    User getUser(SecurityUser currentUser);
    boolean updateUser(User user);
    boolean updateUserImage(MultipartFile image);
    User setPassword(NewPassword newPasswordDto, SecurityUser currentUser);

    UserEntity checkUserByUsername(String username);

    boolean updateUser(User userDto, SecurityUser currentUser);
}
