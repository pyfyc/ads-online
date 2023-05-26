package com.skypro.adsonline.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skypro.adsonline.configuration.InMemoryUserDetailsConfig;
import com.skypro.adsonline.exception.WrongPasswordException;
import com.skypro.adsonline.model.AvatarEntity;
import com.skypro.adsonline.repository.AvatarRepository;
import com.skypro.adsonline.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.skypro.adsonline.constant.DtoConstantsTest.NEW_PASSWORD_DTO;
import static com.skypro.adsonline.constant.DtoConstantsTest.USER_DTO;
import static com.skypro.adsonline.constant.EntityConstantsTest.*;
import static com.skypro.adsonline.constant.SecurityConstantsTest.SECURITY_USER_NAME;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = InMemoryUserDetailsConfig.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @MockBean
    UserRepository userRepository;

    @MockBean
    AvatarRepository avatarRepository;

    @Test
    @WithUserDetails(SECURITY_USER_NAME)
    public void returnOkWhenSetPassword() throws Exception {
        when(userRepository.findByUsername(any(String.class))).thenReturn(USER3);

        mockMvc.perform(post("http://localhost:" + port + "/users/set_password")
                        .content(objectMapper.writeValueAsString(NEW_PASSWORD_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(SECURITY_USER_NAME)
    public void returnWrongPasswordWhenSetPassword() throws Exception {
        when(userRepository.findByUsername(any(String.class))).thenReturn(USER);

        mockMvc.perform(post("http://localhost:" + port + "/users/set_password")
                        .content(objectMapper.writeValueAsString(NEW_PASSWORD_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof WrongPasswordException));
    }

    @Test
    @WithUserDetails(SECURITY_USER_NAME)
    public void returnOkWhenGetUser() throws Exception {
        when(userRepository.findByUsername(any(String.class))).thenReturn(USER);

        mockMvc.perform(get("http://localhost:" + port + "/users/me"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(SECURITY_USER_NAME)
    public void returnOkWhenUpdateUser() throws Exception {
        when(userRepository.findByUsername(any(String.class))).thenReturn(USER);

        mockMvc.perform(patch("http://localhost:" + port + "/users/me")
                        .content(objectMapper.writeValueAsString(USER_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(SECURITY_USER_NAME)
    public void returnOkWhenUpdateUserImage() throws Exception {
        final MockMultipartFile file = new MockMultipartFile("image", "image.jpeg", "image/jpeg", new byte[] { 0x00 });

        when(userRepository.findByUsername(any(String.class))).thenReturn(USER);
        when(avatarRepository.save(any(AvatarEntity.class))).thenReturn(AVATAR_IMAGE);

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("http://localhost:" + port + "/users/me/image");
        builder.with(request -> { request.setMethod("PATCH"); return request; });

        mockMvc.perform(builder.file(file))
                .andExpect(status().isOk());
    }
}