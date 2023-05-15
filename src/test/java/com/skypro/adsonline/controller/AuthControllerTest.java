package com.skypro.adsonline.controller;

import com.skypro.adsonline.dto.LoginReq;
import com.skypro.adsonline.dto.RegisterReq;
import com.skypro.adsonline.dto.Role;
import com.skypro.adsonline.model.UserEntity;
import com.skypro.adsonline.security.JpaUserDetailsService;
import com.skypro.adsonline.security.SecurityUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PasswordEncoder encoder;

    @LocalServerPort
    private int port;

    @MockBean
    private JpaUserDetailsService userDetailsService;

    @Test
    public void login() {
        LoginReq loginReq = new LoginReq();
        loginReq.setUsername("test@gmail.com");
        loginReq.setPassword("12345");

        UserEntity user = new UserEntity();
        user.setId(1);
        user.setUsername("test@gmail.com");
        user.setPassword(encoder.encode("12345"));

        SecurityUser userDetails = new SecurityUser();
        userDetails.setUser(user);

        when(userDetailsService.loadUserByUsername(any(String.class))).thenReturn(userDetails);

        ResponseEntity<?> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/login",
                loginReq,
                LoginReq.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void register() {
        RegisterReq registerReq = new RegisterReq();
        registerReq.setUsername("test@gmail.com");
        registerReq.setPassword("12345");
        registerReq.setFirstName("Vasya");
        registerReq.setLastName("Pupkin");
        registerReq.setPhone("+79161234567");
        registerReq.setRole(Role.USER);

        ResponseEntity<?> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/register",
                registerReq,
                RegisterReq.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}