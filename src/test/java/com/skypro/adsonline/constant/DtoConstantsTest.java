package com.skypro.adsonline.constant;

import com.skypro.adsonline.dto.*;

import java.time.LocalDateTime;
import java.time.Month;

import static com.skypro.adsonline.constant.SecurityConstantsTest.SECURITY_USER_PASSWORD;
import static com.skypro.adsonline.utils.DateUtils.localDateTimeToLong;

public class DtoConstantsTest {
    public static final User USER_DTO;

    public static final Ads ADS_DTO;

    public static final FullAds FULL_ADS_DTO;

    public static final CreateAds CREATE_ADS_DTO;

    public static final Comment ADS_COMMENT_DTO;

    public static final RegisterReq REGISTER_REQ_DTO;

    public static final NewPassword NEW_PASSWORD_DTO;

    static {
        USER_DTO = new User();
        USER_DTO.setEmail("user@mail.com");
        USER_DTO.setFirstName("firstName");
        USER_DTO.setId(1);
        USER_DTO.setLastName("lastName");
        USER_DTO.setPhone("phone");

        ADS_DTO = new Ads();
        ADS_DTO.setAuthor(1);
        ADS_DTO.setImage("/ads-image/1");
        ADS_DTO.setPk(1);
        ADS_DTO.setPrice(1000);
        ADS_DTO.setTitle("title");

        FULL_ADS_DTO = new FullAds();
        FULL_ADS_DTO.setAuthorFirstName("firstName");
        FULL_ADS_DTO.setAuthorLastName("lastName");
        FULL_ADS_DTO.setDescription("description");
        FULL_ADS_DTO.setEmail("user@mail.com");
        FULL_ADS_DTO.setImage("/ads-image/1");
        FULL_ADS_DTO.setPhone("phone");
        FULL_ADS_DTO.setPk(1);
        FULL_ADS_DTO.setPrice(1000);
        FULL_ADS_DTO.setTitle("title");

        CREATE_ADS_DTO = new CreateAds();
        CREATE_ADS_DTO.setDescription("description");
        CREATE_ADS_DTO.setPrice(1000);
        CREATE_ADS_DTO.setTitle("title");

        ADS_COMMENT_DTO = new Comment();
        ADS_COMMENT_DTO.setAuthor(1);
        ADS_COMMENT_DTO.setCreatedAt(localDateTimeToLong(LocalDateTime.of(2023, Month.JANUARY, 1, 12, 0, 0)));
        ADS_COMMENT_DTO.setPk(1);
        ADS_COMMENT_DTO.setText("text");

        REGISTER_REQ_DTO = new RegisterReq();
        REGISTER_REQ_DTO.setUsername("user@mail.com");
        REGISTER_REQ_DTO.setPassword("password");
        REGISTER_REQ_DTO.setFirstName("firstName");
        REGISTER_REQ_DTO.setLastName("lastName");
        REGISTER_REQ_DTO.setPhone("phone");
        REGISTER_REQ_DTO.setRole(Role.USER);

        NEW_PASSWORD_DTO = new NewPassword();
        NEW_PASSWORD_DTO.setCurrentPassword(SECURITY_USER_PASSWORD);
        NEW_PASSWORD_DTO.setNewPassword("new-password");
    }
}
