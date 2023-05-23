package com.skypro.adsonline.service;

import com.skypro.adsonline.dto.NewPassword;
import com.skypro.adsonline.dto.User;
import com.skypro.adsonline.model.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    User setPassword(NewPassword newPasswordDto, UserDetails currentUser);
    User getUser(UserDetails currentUser);
    boolean updateUser(User userDto, UserDetails currentUser);
    UserEntity checkUserByUsername(String username);
    User updateUserImage(MultipartFile image, UserDetails currentUser) throws IOException;
}
