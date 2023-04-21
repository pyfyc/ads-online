package com.skypro.adsonline.controller;

import com.skypro.adsonline.dto.Ads;
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
                                    schema = @Schema(implementation = Ads.class)
                            )
                    )
            },
            tags = "Обновление фотографии"
    )
    @PatchMapping("/image/{id}")
    public ResponseEntity<?> updateImage(@PathVariable int id, @RequestParam MultipartFile file) {
        if (imageService.updateAdsImage(id, file)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
