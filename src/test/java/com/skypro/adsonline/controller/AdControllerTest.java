package com.skypro.adsonline.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skypro.adsonline.configuration.InMemoryUserDetailsConfig;
import com.skypro.adsonline.dto.ResponseWrapperAds;
import com.skypro.adsonline.exception.AdNotFoundException;
import com.skypro.adsonline.exception.UserNotFoundException;
import com.skypro.adsonline.model.AdEntity;
import com.skypro.adsonline.model.AdImageEntity;
import com.skypro.adsonline.model.UserEntity;
import com.skypro.adsonline.repository.AdImageRepository;
import com.skypro.adsonline.repository.AdRepository;
import com.skypro.adsonline.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.skypro.adsonline.constant.DtoConstantsTest.*;
import static com.skypro.adsonline.constant.EntityConstantsTest.*;
import static com.skypro.adsonline.constant.SecurityConstantsTest.SECURITY_USER_NAME;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = InMemoryUserDetailsConfig.class)
@AutoConfigureMockMvc
class AdControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @MockBean
    UserRepository userRepository;

    @MockBean
    AdRepository adRepository;

    @MockBean
    AdImageRepository adImageRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    protected WebApplicationContext context;

    @Test
    public void returnOkWhenGetAllAds() throws Exception {
        ResponseEntity<ResponseWrapperAds> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/ads",
                null,
                ResponseWrapperAds.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @WithUserDetails(SECURITY_USER_NAME)
    public void returnOkWhenAddAd() throws Exception {
        byte[] fileContent = new byte[] { 0x00 };
        MockPart filePart = new MockPart("image", "image.jpeg", fileContent);

        byte[] adsContent = objectMapper.writeValueAsString(CREATE_ADS_DTO).getBytes(UTF_8);
        MockPart adsPart = new MockPart("properties", "createAdsDto", adsContent);
        adsPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        when(userRepository.findByUsername(any(String.class))).thenReturn(USER);
        when(adRepository.save(any(AdEntity.class))).thenReturn(AD);
        when(adImageRepository.save(any(AdImageEntity.class))).thenReturn(AD_IMAGE);

        mockMvc.perform(multipart("http://localhost:" + port + "/ads")
                        .part(adsPart)
                        .part(filePart)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.author").value(ADS_DTO.getAuthor()))
                .andExpect(jsonPath("$.image").value(ADS_DTO.getImage()))
                .andExpect(jsonPath("$.pk").value(ADS_DTO.getPk()))
                .andExpect(jsonPath("$.price").value(ADS_DTO.getPrice()))
                .andExpect(jsonPath("$.title").value(ADS_DTO.getTitle()));
    }

    @Test
    @WithUserDetails(SECURITY_USER_NAME)
    public void returnUserNotFoundWhenAddAd() throws Exception {
        byte[] fileContent = new byte[] { 0x00 };
        MockPart filePart = new MockPart("image", "image.jpeg", fileContent);

        byte[] adsContent = objectMapper.writeValueAsString(CREATE_ADS_DTO).getBytes(UTF_8);
        MockPart adsPart = new MockPart("properties", "createAdsDto", adsContent);
        adsPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(multipart("http://localhost:" + port + "/ads")
                        .part(adsPart)
                        .part(filePart)
                )
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException));
    }

    @Test
    @WithUserDetails(SECURITY_USER_NAME)
    public void returnOkWhenGetAd() throws Exception {
        Integer id = 1;

        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.of(AD));

        mockMvc.perform(get("http://localhost:" + port + "/ads/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorFirstName").value(FULL_ADS_DTO.getAuthorFirstName()))
                .andExpect(jsonPath("$.authorLastName").value(FULL_ADS_DTO.getAuthorLastName()))
                .andExpect(jsonPath("$.description").value(FULL_ADS_DTO.getDescription()))
                .andExpect(jsonPath("$.email").value(FULL_ADS_DTO.getEmail()))
                .andExpect(jsonPath("$.image").value(FULL_ADS_DTO.getImage()))
                .andExpect(jsonPath("$.phone").value(FULL_ADS_DTO.getPhone()))
                .andExpect(jsonPath("$.price").value(FULL_ADS_DTO.getPrice()))
                .andExpect(jsonPath("$.title").value(FULL_ADS_DTO.getTitle()));
    }

    @Test
    @WithUserDetails(SECURITY_USER_NAME)
    public void returnAdNotFoundWhenGetAd() throws Exception {
        Integer id = 1;

        mockMvc.perform(get("http://localhost:" + port + "/ads/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AdNotFoundException));
    }

    @Test
    public void returnUnauthorizedWhenGetAd() throws Exception {
        Integer id = 1;

        mockMvc.perform(get("http://localhost:" + port + "/ads/{id}", id))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(SECURITY_USER_NAME)
    public void returnOkWhenRemoveAd() throws Exception {
        Integer id = 1;

        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.of(AD));

        mockMvc.perform(delete("http://localhost:" + port + "/ads/{id}", id))
                .andExpect(status().isOk());

        verify(adRepository, times(1)).delete(AD);
    }

    @Test
    @WithUserDetails(SECURITY_USER_NAME)
    public void returnForbiddenWhenRemoveAdOfAnotherUser() throws Exception {
        Integer id = 1;

        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.of(AD2));

        mockMvc.perform(delete("http://localhost:" + port + "/ads/{id}", id))
                .andExpect(status().isForbidden())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    @WithUserDetails(SECURITY_USER_NAME)
    public void returnOkWhenUpdateAd() throws Exception {
        Integer id = 1;

        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.of(AD));

        mockMvc.perform(patch("http://localhost:" + port + "/ads/{id}", id)
                        .content(objectMapper.writeValueAsString(ADS_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value(ADS_DTO.getAuthor()))
                .andExpect(jsonPath("$.image").value(ADS_DTO.getImage()))
                .andExpect(jsonPath("$.pk").value(ADS_DTO.getPk()))
                .andExpect(jsonPath("$.price").value(ADS_DTO.getPrice()))
                .andExpect(jsonPath("$.title").value(ADS_DTO.getTitle()));
    }

    @Test
    @WithUserDetails(SECURITY_USER_NAME)
    public void returnOkWhenGetAllAdsForMe() throws Exception {
        final List<AdEntity> adsList = new ArrayList<>();
        adsList.add(AD);

        when(userRepository.findByUsername(any(String.class))).thenReturn(USER);
        when(adRepository.findByAuthor(any(UserEntity.class))).thenReturn(adsList);

        mockMvc.perform(get("http://localhost:" + port + "/ads/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(adsList.size()))
                .andExpect(jsonPath("$.results[0].author").value(ADS_DTO.getAuthor()))
                .andExpect(jsonPath("$.results[0].image").value(ADS_DTO.getImage()))
                .andExpect(jsonPath("$.results[0].pk").value(ADS_DTO.getPk()))
                .andExpect(jsonPath("$.results[0].price").value(ADS_DTO.getPrice()))
                .andExpect(jsonPath("$.results[0].title").value(ADS_DTO.getTitle()));
    }

    @Test
    @WithUserDetails(SECURITY_USER_NAME)
    public void returnOkWhenUpdateAdImage() throws Exception {
        Integer id = 1;
        final MockMultipartFile file = new MockMultipartFile("image", "image.jpeg", "image/jpeg", new byte[] { 0x00 });

        when(adRepository.findById(any(Integer.class))).thenReturn(Optional.of(AD));
        when(adImageRepository.save(any(AdImageEntity.class))).thenReturn(AD_IMAGE);

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("http://localhost:" + port + "/ads/{id}/image", id);
        builder.with(request -> { request.setMethod("PATCH"); return request; });

        mockMvc.perform(builder.file(file))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(SECURITY_USER_NAME)
    public void returnOkWhenGetAdsByTitleLike() throws Exception {
        final List<AdEntity> adsList = new ArrayList<>();
        adsList.add(AD);

        String title = "Ads";

        when(adRepository.findByTitleContainingIgnoreCase(any(String.class))).thenReturn(adsList);

        mockMvc.perform(get("http://localhost:" + port + "/ads/title-like")
                        .queryParam("title", title)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(adsList.size()))
                .andExpect(jsonPath("$.results[0].author").value(ADS_DTO.getAuthor()))
                .andExpect(jsonPath("$.results[0].image").value(ADS_DTO.getImage()))
                .andExpect(jsonPath("$.results[0].pk").value(ADS_DTO.getPk()))
                .andExpect(jsonPath("$.results[0].price").value(ADS_DTO.getPrice()))
                .andExpect(jsonPath("$.results[0].title").value(ADS_DTO.getTitle()));
    }
}