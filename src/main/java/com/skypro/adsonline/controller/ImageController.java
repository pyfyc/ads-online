package com.skypro.adsonline.controller;

import com.skypro.adsonline.exception.AdNotFoundException;
import com.skypro.adsonline.exception.UserNotFoundException;
import com.skypro.adsonline.model.AdEntity;
import com.skypro.adsonline.model.AdImageEntity;
import com.skypro.adsonline.model.AvatarEntity;
import com.skypro.adsonline.model.UserEntity;
import com.skypro.adsonline.repository.AdImageRepository;
import com.skypro.adsonline.repository.AdRepository;
import com.skypro.adsonline.repository.AvatarRepository;
import com.skypro.adsonline.repository.UserRepository;
import com.skypro.adsonline.service.ImageService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import static com.skypro.adsonline.constant.ErrorMessage.AD_NOT_FOUND_MSG;
import static com.skypro.adsonline.constant.ErrorMessage.USER_NOT_FOUND_MSG;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@Transactional
public class ImageController {
    private final ImageService imageService;
    private final AdImageRepository adImageRepository;
    private final UserRepository userRepository;
    private final AdRepository adRepository;
    private final AvatarRepository avatarRepository;

    @Value("${users.avatar.dir.path}")
    private String avatarsDir;

    @Value("${ads.image.dir.path}")
    private String adsImageDir;

    public ImageController(ImageService imageService, AdImageRepository adImageRepository, UserRepository userRepository, AdRepository adRepository, AvatarRepository avatarRepository) {
        this.imageService = imageService;
        this.adImageRepository = adImageRepository;
        this.userRepository = userRepository;
        this.adRepository = adRepository;
        this.avatarRepository = avatarRepository;
    }

    /**
     * Get user avatar from disk by user id.
     * @param userId user id
     * @param response user avatar picture sent to front-end
     * @throws IOException
     */
    @GetMapping("/avatars/{userId}")
    public void getAvatarFromDisk(@PathVariable Integer userId, HttpServletResponse response) throws IOException {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MSG.formatted(userId)));

        Optional<AvatarEntity> imageDetails = avatarRepository.findByUser(user);
        if (imageDetails.isEmpty()) {
            return;
        }

        Path filePath = Path.of(avatarsDir, userId + "." + imageDetails.get().getFileExtension());
        imageService.getImageFromDisk(response, filePath, imageDetails.get());
    }

    /**
     * Get ad image from disk by its id.
     * @param adId ad id
     * @param response ad image picture sent to front-end
     * @throws IOException
     */
    @GetMapping("/ads-image/{adId}")
    public void getAdImageFromDisk(@PathVariable Integer adId, HttpServletResponse response) throws IOException {
        AdEntity ad = adRepository.findById(adId).orElseThrow(() -> new AdNotFoundException(AD_NOT_FOUND_MSG.formatted(adId)));

        Optional<AdImageEntity> imageDetails = adImageRepository.findByAd(ad);
        if (imageDetails.isEmpty()) {
            return;
        }

        Path filePath = Path.of(adsImageDir, ad.getAuthor().getId() + "-" + ad.getId() + "." + imageDetails.get().getFileExtension());
        imageService.getImageFromDisk(response, filePath, imageDetails.get());
    }
}
