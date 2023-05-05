package com.skypro.adsonline.controller;

import com.skypro.adsonline.dto.Comment;
import com.skypro.adsonline.dto.ResponseWrapperComment;
import com.skypro.adsonline.service.CommentService;
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
public class CommentController {

    private final CommentService commentService;

    @Operation(
            summary = "Получить комментарии объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperComment.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            },
            tags = "Комментарии"
    )
    @GetMapping("{id}/comments")
    public ResponseEntity<ResponseWrapperComment> getComments(@PathVariable Integer id) {
        ResponseWrapperComment comments = commentService.getComments(id);
        if(comments != null) {
            return ResponseEntity.ok(comments);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



    @Operation(
            summary = "Добавить комментарий к объявлению",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Comment.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            },
            tags = "Комментарии"
    )
    @PostMapping("/{id}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Integer id, @RequestBody Comment comment) {
        Comment newComment = commentService.addComment(id, comment);
        if(newComment != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(
            summary = "Удалить комментарий",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            },
            tags = "Комментарии"
    )
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer adId,
                                              @PathVariable Integer commentId) {
        commentService.deleteComment(adId, commentId);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Обновить комментарий",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Comment.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            },
            tags = "Комментарии"
    )
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable Integer adId,
            @PathVariable Integer commentId,
            @RequestBody Comment comment) {
        Comment updateComment = commentService.updateComment(adId, commentId, comment);
        if(updateComment != null) {
            return ResponseEntity.ok(updateComment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
