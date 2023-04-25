package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.dto.RegisterReq;
import com.skypro.adsonline.dto.Role;
import com.skypro.adsonline.repository.UserRepository;
import com.skypro.adsonline.service.AuthService;
import com.skypro.adsonline.utils.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    public AuthServiceImpl(UserRepository userRepository, UserMapper mappingUtils) {
        this.userRepository = userRepository;
        this.userMapper = mappingUtils;
    }

    @Override
    public boolean login(String userName, String password) {
        return true;
    }

    @Override
    public boolean register(RegisterReq registerReq, Role role) {
        try {
            registerReq.setRole(role);
            userRepository.save(userMapper.mapToUserEntity(registerReq));
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }
}
