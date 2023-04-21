package com.skypro.adsonline.controller;

import com.skypro.adsonline.dto.Ads;
import com.skypro.adsonline.dto.Comment;
import com.skypro.adsonline.dto.CreateAds;
import com.skypro.adsonline.dto.User;
import com.skypro.adsonline.service.AdService;
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
@RequestMapping("ads")
public class AdController {

    private final AdService adService;

    @Operation(
            summary = "Добавление объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Объявление добавлено",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Ads.class)
                            )
                    )
            },
            tags = "Объявления"
    )
    @PostMapping
    public ResponseEntity<?> addAds(@RequestBody Object properties, @RequestBody byte[] image) {
        if(adService.addAds(properties, image)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @Operation(
            summary = "Получение комментариев",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Комментарии получены",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Comment.class)
                            )
                    )
            },
            tags = "Объявления"
    )
    @GetMapping("/{ad_pk}/comments")
    public ResponseEntity<?> getComments(@PathVariable("ad_pk") String adPk) {
        if(adService.getComments(adPk)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @Operation(
            summary = "Получение комментариев",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Комментарии получены",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Comment.class)
                            )
                    )
            },
            tags = "Объявления"
    )
    @GetMapping("{ad_pk}/comments/{id}")
    public ResponseEntity<?> getComments(@PathVariable("ad_pk") String adPk, @PathVariable Integer id) {
        if(adService.getComments(adPk, id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @Operation(
            summary = "Добавление комментария",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Комментарий добавлен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Comment.class)
                            )
                    )
            },
            tags = "Объявления"
    )
    @PostMapping("/{ad_pk}/comments")
    public ResponseEntity<?> addComments(@PathVariable("ad_pk") String adPk, @RequestBody Comment comment) {
        if(adService.addComments(adPk, comment)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(
            summary = "Получение полного объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Объявление получено",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Ads.class)
                            )
                    )
            },
            tags = "Объявления"
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getFullAd(@PathVariable("id") Integer id) {
        if(adService.getFullAd(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
            summary = "Удаление объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Объявление удалено",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Ads.class)
                            )
                    )
            },
            tags = "Объявления"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAds(@PathVariable("id") Integer id) {
        if(adService.removeAds(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
            summary = "Обновление объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Объявление обновлено",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Ads.class)
                            )
                    )
            },
            tags = "Объявления"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAds(@PathVariable("id") Integer id, @RequestBody CreateAds ads) {
        if(adService.updateAds(id, ads)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
            summary = "Удаление комментария",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Комментарий удален",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Comment.class)
                            )
                    )
            },
            tags = "Объявления"
    )
    @DeleteMapping("/ads/{ad_pk}/comments/{id}")
    public ResponseEntity<?> deleteComments(@PathVariable("id") Integer id, @PathVariable("ad_pk") String adPk) {
        if(adService.deleteComments(id, adPk)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
            summary = "Обновления комментария",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Объявление обновлено",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Ads.class)
                            )
                    )
            },
            tags = "Объявления"
    )
    @PatchMapping("/ads/{ad_pk}/comments/{id}")
    public ResponseEntity<?> updateComments(@PathVariable("id") Integer id, @PathVariable("ad_pk") String adPk, @RequestBody Comment comment) {
        if(adService.updateComments(id, adPk, comment)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
            summary = "Получение моих объявлений",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Объявления получены",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Ads.class)
                            )
                    )
            },
            tags = "Объявления"
    )
    @GetMapping("/ads/me")
    public ResponseEntity<?> getAdsMe() {
        if(adService.getAdsMe()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
