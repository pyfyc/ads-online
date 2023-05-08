package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.dto.NewPassword;
import com.skypro.adsonline.dto.User;
import com.skypro.adsonline.exception.UserNotFoundException;
import com.skypro.adsonline.exception.WrongPasswordException;
import com.skypro.adsonline.model.UserEntity;
import com.skypro.adsonline.repository.UserRepository;
import com.skypro.adsonline.security.SecurityUser;
import com.skypro.adsonline.service.UserService;
import com.skypro.adsonline.utils.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.skypro.adsonline.constant.ErrorMessage.USER_NOT_FOUND_MSG;
import static com.skypro.adsonline.constant.ErrorMessage.WRONG_PASS_MSG;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
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
    public boolean updateUserImage(MultipartFile image) {
        return false;
    }

    @Override
    public User setPassword(NewPassword newPasswordDto, SecurityUser currentUser) {
        UserEntity user = checkUserByUsername(currentUser.getUsername());
        String encryptedPassword = user.getPassword();
        if (!passwordEncoder.matches(newPasswordDto.getCurrentPassword(), encryptedPassword)) {
            throw new WrongPasswordException(WRONG_PASS_MSG.formatted(currentUser.getUsername()));
        }

        String newPassword = newPasswordDto.getNewPassword();

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return userMapper.mapToUserDto(user);

    }

    @Override
    public UserEntity checkUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(USER_NOT_FOUND_MSG.formatted(username));
        }
        return user;
    }

    @Override
    public boolean updateUser(User userDto, SecurityUser currentUser) {
        UserEntity user = checkUserByUsername(currentUser.getUsername());

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        userRepository.save(user);
        return true;
    }
}
