package com.skypro.adsonline.controller;

import com.skypro.adsonline.dto.NewPassword;
import com.skypro.adsonline.dto.User;
import com.skypro.adsonline.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Изменение пароля пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пароль изменен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = NewPassword.class)
                            )
                    )
            },
            tags = "Пользователи"
    )
    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPassword password) {
        if (userService.setPassword(password.getCurrentPassword(), password.getNewPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(
            summary = "Получение информации о пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация получена"
                    )
            },
            tags = "Пользователи"
    )
    @GetMapping("/me")
    public ResponseEntity<?> getUser() {
        if (userService.getUser()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(
            summary = "Изменение пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь изменен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                    )
            },
            tags = "Пользователи"
    )
    @PatchMapping("/me")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        if (userService.updateUser(user)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(
            summary = "Изменение фото пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Фото изменено",
                            content = @Content(
                                    mediaType = MediaType.MULTIPART_FORM_DATA_VALUE
                            )
                    )
            },
            tags = "Пользователи"
    )
    @PatchMapping("/me/image")
    public ResponseEntity<?> updateUserImage(@RequestBody String image) {
        if (userService.updateUserImage(image)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
