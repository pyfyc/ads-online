package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.dto.RegisterReq;
import com.skypro.adsonline.dto.Role;
import com.skypro.adsonline.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public boolean login(String userName, String password) {
        return true;
    }

    @Override
    public boolean register(RegisterReq registerReq, Role role) {
        return true;
    }
}
