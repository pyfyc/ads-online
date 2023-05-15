package com.skypro.adsonline.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skypro.adsonline.configuration.InMemoryUserDetailsConfig;
import com.skypro.adsonline.dto.ResponseWrapperAds;
import com.skypro.adsonline.model.AdEntity;
import com.skypro.adsonline.repository.AdRepository;
import com.skypro.adsonline.repository.UserRepository;
import com.skypro.adsonline.security.JpaUserDetailsService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockPart;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static com.skypro.adsonline.constant.DtoConstantsTest.ADS_DTO;
import static com.skypro.adsonline.constant.DtoConstantsTest.CREATE_ADS_DTO;
import static com.skypro.adsonline.constant.EntityConstantsTest.ADS;
import static com.skypro.adsonline.constant.EntityConstantsTest.USER;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import(value = InMemoryUserDetailsConfig.class)
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
    private JpaUserDetailsService userDetailsService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    AdRepository adRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    protected WebApplicationContext context;

    @Test
    void returnOkWhenAddAds() throws Exception {
        byte[] fileContent = new byte[] { 0x00 };
        MockPart filePart = new MockPart("image", "image.jpeg", fileContent);

        byte[] adsContent = objectMapper.writeValueAsString(CREATE_ADS_DTO).getBytes(UTF_8);
        MockPart adsPart = new MockPart("properties", "createAdsDto", adsContent);
        adsPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        when(userRepository.findByUsername(any(String.class))).thenReturn(USER);
        when(adRepository.save(any(AdEntity.class))).thenReturn(ADS);
        //when(imageRepository.save(any(Image.class))).thenReturn(IMAGE);

        mockMvc.perform(multipart("http://localhost:" + port + "/ads")
                        .part(adsPart)
                        .part(filePart)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.author").value(ADS_DTO.getAuthor()))
                //.andExpect(jsonPath("$.image").value(ADS_DTO.getImage()))
                .andExpect(jsonPath("$.pk").value(ADS_DTO.getPk()))
                .andExpect(jsonPath("$.price").value(ADS_DTO.getPrice()))
                .andExpect(jsonPath("$.title").value(ADS_DTO.getTitle()));
    }

    @Test
    public void getAllAds() throws Exception {
        ResponseEntity<ResponseWrapperAds> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/ads",
                null,
                ResponseWrapperAds.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}