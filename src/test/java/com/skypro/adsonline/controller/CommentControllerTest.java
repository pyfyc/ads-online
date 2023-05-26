package com.skypro.adsonline.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skypro.adsonline.configuration.InMemoryUserDetailsConfig;
import com.skypro.adsonline.exception.CommentNotFoundException;
import com.skypro.adsonline.model.CommentEntity;
import com.skypro.adsonline.repository.AdRepository;
import com.skypro.adsonline.repository.CommentRepository;
import com.skypro.adsonline.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.skypro.adsonline.constant.DtoConstantsTest.ADS_COMMENT_DTO;
import static com.skypro.adsonline.constant.EntityConstantsTest.*;
import static com.skypro.adsonline.constant.SecurityConstantsTest.SECURITY_USER_NAME;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = InMemoryUserDetailsConfig.class)
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @MockBean
    CommentRepository commentRepository;

    @MockBean
    AdRepository adRepository;

    @MockBean
    UserService userService;

    @Test
    @WithUserDetails(SECURITY_USER_NAME)
    public void returnOkWhenGetComments() throws Exception{
        final List<CommentEntity> commentsList = new ArrayList<>();
        commentsList.add(AD_COMMENT);

        Integer id = 1;

        when(commentRepository.findByAdId(any(Integer.class))).thenReturn(commentsList);

        mockMvc.perform(get("http://localhost:" + port + "/ads/{id}/comments", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(commentsList.size()))
                .andExpect(jsonPath("$.results[0].author").value(ADS_COMMENT_DTO.getAuthor()))
                .andExpect(jsonPath("$.results[0].createdAt").value(ADS_COMMENT_DTO.getCreatedAt()))
                .andExpect(jsonPath("$.results[0].pk").value(ADS_COMMENT_DTO.getPk()))
                .andExpect(jsonPath("$.results[0].text").value(ADS_COMMENT_DTO.getText()));
    }

    @Test
    @WithUserDetails(SECURITY_USER_NAME)
    public void returnOkWhenAddComment() throws Exception {
        Integer id = 1;

        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.of(AD));
        when(userService.checkUserByUsername(any(String.class))).thenReturn(USER);
        when(commentRepository.save(any(CommentEntity.class))).thenReturn(AD_COMMENT);

        mockMvc.perform(post("http://localhost:" + port + "/ads/{id}/comments", id)
                        .content(objectMapper.writeValueAsString(ADS_COMMENT_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value(ADS_COMMENT_DTO.getAuthor()))
                .andExpect(jsonPath("$.createdAt").value(ADS_COMMENT_DTO.getCreatedAt()))
                .andExpect(jsonPath("$.pk").value(ADS_COMMENT_DTO.getPk()))
                .andExpect(jsonPath("$.text").value(ADS_COMMENT_DTO.getText()));
    }

    @Test
    @WithUserDetails(SECURITY_USER_NAME)
    public void returnOkWhenDeleteComment() throws Exception {
        Integer adId = 1;
        Integer commentId = 1;

        when(commentRepository.findById(any(Integer.class))).thenReturn(Optional.of(AD_COMMENT));

        mockMvc.perform(delete("http://localhost:" + port + "/ads/{adId}/comments/{commentId}", adId, commentId))
                .andExpect(status().isOk());

        verify(commentRepository, times(1)).delete(AD_COMMENT);
    }

    @Test
    @WithUserDetails(SECURITY_USER_NAME)
    public void returnCommentNotFoundWhenDeleteComment() throws Exception {
        Integer adId = 1;
        Integer commentId = 1;

        mockMvc.perform(delete("http://localhost:" + port + "/ads/{adId}/comments/{commentId}", adId, commentId))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CommentNotFoundException));
    }

    @Test
    @WithUserDetails(SECURITY_USER_NAME)
    public void returnOkWhenUpdateComment() throws Exception {
        Integer adId = 1;
        Integer commentId = 1;

        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.of(AD));
        when(commentRepository.findById(any(Integer.class))).thenReturn(Optional.of(AD_COMMENT));

        mockMvc.perform(patch("http://localhost:" + port + "/ads/{adId}/comments/{commentId}", adId, commentId)
                        .content(objectMapper.writeValueAsString(ADS_COMMENT_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value(ADS_COMMENT_DTO.getAuthor()))
                .andExpect(jsonPath("$.createdAt").value(ADS_COMMENT_DTO.getCreatedAt()))
                .andExpect(jsonPath("$.pk").value(ADS_COMMENT_DTO.getPk()))
                .andExpect(jsonPath("$.text").value(ADS_COMMENT_DTO.getText()));
    }
}