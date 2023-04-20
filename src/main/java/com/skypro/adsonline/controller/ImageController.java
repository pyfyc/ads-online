package com.skypro.adsonline.controller;

import com.skypro.adsonline.dto.Image;
import com.skypro.adsonline.dto.LoginReq;
import com.skypro.adsonline.service.ImageService;
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
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class ImageController {

    private ImageService imageService;

    @Operation(
            summary = "Обновление фотографии",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Фотография обновлена",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Image.class)
                            )
                    )
            },
            tags = "Обновление фотографии"
    )
    @PatchMapping("/update-image")
    public ResponseEntity<?> updateImage(@PathVariable int id, MultipartFile file) {
        return ResponseEntity.ok(imageService.updateAdsImage(id, file));
    }
}
