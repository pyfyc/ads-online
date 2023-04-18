package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.dto.User;
import com.skypro.adsonline.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public boolean setPassword(String currentPassword, String newPassword) {
        return false;
    }

    @Override
    public boolean getUser() {
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public boolean updateUserImage(String image) {
        return false;
    }
}
