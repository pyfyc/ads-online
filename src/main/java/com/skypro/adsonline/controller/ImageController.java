package com.skypro.adsonline.controller;

import com.skypro.adsonline.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class ImageController {
    private final UserService userService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hi from ImageController!");
    }

    @GetMapping("/avatars/{id}")
    public ResponseEntity<byte[]> getAvatarFromDb(@PathVariable Integer id) {
        byte[] avatar = userService.getAvatarFromDb(id);
        if (avatar != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(avatar.length);

            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
