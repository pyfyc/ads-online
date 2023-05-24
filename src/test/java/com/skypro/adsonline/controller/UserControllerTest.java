package com.skypro.adsonline.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skypro.adsonline.configuration.InMemoryUserDetailsConfig;
import com.skypro.adsonline.repository.UserRepository;
import com.skypro.adsonline.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static com.skypro.adsonline.constant.DtoConstantsTest.NEW_PASSWORD_DTO;
import static com.skypro.adsonline.constant.EntityConstantsTest.USER;
import static com.skypro.adsonline.constant.SecurityConstantsTest.SECURITY_USER_NAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    PasswordEncoder passwordEncoder;

    @MockBean
    UserService userService;

    @Test
    @WithUserDetails(SECURITY_USER_NAME)
    public void returnOkWhenSetPassword() throws Exception {
        when(userRepository.findByUsername(any(String.class))).thenReturn(USER);
        when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(true);

        mockMvc.perform(post("http://localhost:" + port + "/users/set_password")
                        .content(objectMapper.writeValueAsString(NEW_PASSWORD_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(SECURITY_USER_NAME)
    public void returnOkWhenGetUser() throws Exception {
        when(userRepository.findByUsername(any(String.class))).thenReturn(USER);

        mockMvc.perform(get("http://localhost:" + port + "/users/me"))
                .andExpect(status().isOk());
    }

    @Test
    void updateUser() {
    }

    @Test
    void updateUserImage() {
    }
}