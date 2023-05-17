package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.dto.NewPassword;
import com.skypro.adsonline.dto.User;
import com.skypro.adsonline.exception.UserNotFoundException;
import com.skypro.adsonline.exception.WrongPasswordException;
import com.skypro.adsonline.model.UserEntity;
import com.skypro.adsonline.repository.UserRepository;
import com.skypro.adsonline.service.UserService;
import com.skypro.adsonline.utils.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.skypro.adsonline.constant.ErrorMessage.USER_NOT_FOUND_MSG;
import static com.skypro.adsonline.constant.ErrorMessage.WRONG_PASS_MSG;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Value("${users.avatar.dir.path}")
    private String avatarsDir;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getUser(UserDetails currentUser) {
        UserEntity user = userRepository.findByUsername(currentUser.getUsername());
        if (user != null) {
            return userMapper.mapToUserDto(user);
        } else {
            return null;
        }
    }

    @Override
    public boolean updateUserImage(MultipartFile image, UserDetails currentUser) throws IOException {
        UserEntity user = checkUserByUsername(currentUser.getUsername());

        Path filePath = Path.of(avatarsDir, user.getId() + "." + getExtension(image.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = image.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);) {
            bis.transferTo(bos);
        }

        user.setImageDb(image.getBytes());
        user.setImage("/" + avatarsDir + "/" + user.getId());
        userRepository.save(user);
        return true;
    }

    @Override
    public User setPassword(NewPassword newPasswordDto, UserDetails currentUser) {
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
    public boolean updateUser(User userDto, UserDetails currentUser) {
        UserEntity user = checkUserByUsername(currentUser.getUsername());

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        userRepository.save(user);
        return true;
    }

    public byte[] getAvatarFromDb(Integer userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MSG.formatted(userId)));
        return user.getImageDb();
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
