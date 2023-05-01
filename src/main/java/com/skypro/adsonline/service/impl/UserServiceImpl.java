package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.dto.User;
import com.skypro.adsonline.model.UserEntity;
import com.skypro.adsonline.repository.UserRepository;
import com.skypro.adsonline.security.SecurityUser;
import com.skypro.adsonline.service.UserService;
import com.skypro.adsonline.utils.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public boolean setPassword(String currentPassword, String newPassword) {
        return false;
    }

    @Override
    public User getUser(SecurityUser currentUser) {
        UserEntity user = userRepository.findByUsername(currentUser.getUsername());
        if (user != null) {
            return userMapper.mapToUserDto(user);
        } else {
            return null;
        }
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public boolean updateUserImage(MultipartFile image) {
        return false;
    }
}
