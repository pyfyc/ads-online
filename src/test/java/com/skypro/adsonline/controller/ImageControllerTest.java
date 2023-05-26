package com.skypro.adsonline.controller;

import com.skypro.adsonline.configuration.InMemoryUserDetailsConfig;
import com.skypro.adsonline.model.AdEntity;
import com.skypro.adsonline.model.UserEntity;
import com.skypro.adsonline.repository.AdImageRepository;
import com.skypro.adsonline.repository.AdRepository;
import com.skypro.adsonline.repository.AvatarRepository;
import com.skypro.adsonline.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static com.skypro.adsonline.constant.EntityConstantsTest.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = InMemoryUserDetailsConfig.class)
@AutoConfigureMockMvc
class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @MockBean
    UserRepository userRepository;

    @MockBean
    AvatarRepository avatarRepository;

    @MockBean
    AdRepository adRepository;

    @MockBean
    AdImageRepository adImageRepository;

    @Value("${ads.image.dir.path}")
    private String adsImageDir;

    @BeforeEach
    public void init() throws IOException {
        // Create image directory for testing
        Path adsImageTempDir = Path.of(adsImageDir);
        Files.createDirectories(adsImageTempDir.resolve(AVATAR_FILE_NAME).getParent());

        // Create avatar file for testing
        if (!Files.exists(adsImageTempDir.resolve(AVATAR_FILE_NAME))) {
            Files.createFile(adsImageTempDir.resolve(AVATAR_FILE_NAME));
        }

        // Create ad image file for testing
        if (!Files.exists(adsImageTempDir.resolve(AD_IMAGE_FILE_NAME))) {
            Files.createFile(adsImageTempDir.resolve(AD_IMAGE_FILE_NAME));
        }
    }

    @Test
    public void returnOkWhenGetAvatarFromDisk() throws Exception {
        Integer userId = 1;

        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(USER));
        when(avatarRepository.findByUser(any(UserEntity.class))).thenReturn(Optional.ofNullable(AVATAR_IMAGE));

        mockMvc.perform(get("http://localhost:" + port + "/avatars/{userId}", userId))
                .andExpect(status().isOk());
    }

    @Test
    public void returnOkWhenGetAdImageFromDisk() throws Exception {
        Integer adId = 1;

        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.of(AD));
        when(adImageRepository.findByAd(any(AdEntity.class))).thenReturn(Optional.of(AD_IMAGE));

        mockMvc.perform(get("http://localhost:" + port + "/ads-image/{adId}", adId))
                .andExpect(status().isOk());
    }
}