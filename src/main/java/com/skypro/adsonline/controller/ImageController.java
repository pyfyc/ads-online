package com.skypro.adsonline.controller;

import com.skypro.adsonline.dto.User;
import com.skypro.adsonline.exception.UserNotFoundException;
import com.skypro.adsonline.model.ImageEntity;
import com.skypro.adsonline.model.UserEntity;
import com.skypro.adsonline.repository.ImageRepository;
import com.skypro.adsonline.repository.UserRepository;
import com.skypro.adsonline.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.skypro.adsonline.constant.ErrorMessage.USER_NOT_FOUND_MSG;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class ImageController {
    private final ImageService imageService;
    private final UserDetails userDetails;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    @Value("${users.avatar.dir.path}")
    private String avatarsDir;

    public ImageController(ImageService imageService, UserDetails userDetails, UserRepository userRepository, ImageRepository imageRepository, UserRepository userRepository1) {
        this.imageService = imageService;
        this.userDetails = userDetails;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository1;
    }

    @Operation(
            summary = "Обновить аватар авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Фото изменено"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )
            },
            tags = "Пользователи"
    )
    @PatchMapping(value = "/users/me/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<User> updateUserImage(@RequestPart(name = "image") MultipartFile image) throws IOException {
        User user = imageService.updateUserImage(image, userDetails);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Get user avatar from disk by user id.
     * @param id user id
     * @param response user avatar picture sent to front-end
     * @throws IOException
     */
    @GetMapping("/avatars/{id}")
    public void getAvatarFromDisk(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MSG.formatted(id)));
        ImageEntity imageDetails = imageRepository.findByUser(user);
        if (imageDetails == null) {
            return;
        }

        Path filePath = Path.of(avatarsDir, id + "." + imageDetails.getFileExtension());

        try (InputStream is = Files.newInputStream(filePath);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(imageDetails.getMediaType());
            response.setContentLength((int) imageDetails.getFileSize());
            is.transferTo(os);
        }
    }
}
