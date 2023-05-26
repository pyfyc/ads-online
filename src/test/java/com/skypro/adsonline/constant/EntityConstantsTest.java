package com.skypro.adsonline.constant;

import com.skypro.adsonline.dto.Role;
import com.skypro.adsonline.model.*;

import java.time.LocalDateTime;
import java.time.Month;

import static com.skypro.adsonline.constant.SecurityConstantsTest.*;
import static com.skypro.adsonline.utils.DateUtils.localDateTimeToLong;

public class EntityConstantsTest {
    public static final UserEntity USER;
    public static final UserEntity USER2;
    public static final UserEntity USER3;
    public static final AdEntity AD;
    public static final AdEntity AD2;
    public static final CommentEntity AD_COMMENT;
    public static final AdImageEntity AD_IMAGE;
    public static final AvatarEntity AVATAR_IMAGE;

    public static final String AVATAR_FILE_NAME = "1.jpeg";
    public static final String AD_IMAGE_FILE_NAME = "1-1.jpeg";
    public static final String IMAGE_TEST_DIR = "test-images";

    static {
        USER = new UserEntity();
        USER.setId(1);
        USER.setFirstName("firstName");
        USER.setLastName("lastName");
        USER.setPhone("phone");
        USER.setUsername(SECURITY_USER_NAME);
        USER.setPassword(SECURITY_USER_PASSWORD);
        USER.setRole(Role.USER);

        USER2 = new UserEntity();
        USER2.setId(2);
        USER2.setFirstName("firstName2");
        USER2.setLastName("lastName2");
        USER2.setPhone("phone2");
        USER2.setUsername(SECURITY_USER2_NAME);
        USER2.setPassword(SECURITY_USER2_PASSWORD);
        USER2.setRole(Role.USER);

        USER3 = new UserEntity();
        USER3.setId(2);
        USER3.setFirstName("firstName3");
        USER3.setLastName("lastName3");
        USER3.setPhone("phone3");
        USER3.setUsername(SECURITY_USER3_NAME);
        USER3.setPassword(SECURITY_USER3_PASSWORD);
        USER3.setRole(Role.USER);

        AD_IMAGE = new AdImageEntity();
        AD_IMAGE.setId(1);
        AD_IMAGE.setFilePath(IMAGE_TEST_DIR + "\\" + AD_IMAGE_FILE_NAME);
        AD_IMAGE.setFileExtension("jpeg");
        AD_IMAGE.setFileSize(121464);
        AD_IMAGE.setMediaType("image/jpeg");

        AD = new AdEntity();
        AD.setId(1);
        AD.setAuthor(USER);
        AD.setTitle("title");
        AD.setDescription("description");
        AD.setPrice(1000);
        AD.setImage("/ads-image/1");
        AD.setImageEntity(AD_IMAGE);

        AD2 = new AdEntity();
        AD2.setId(2);
        AD2.setAuthor(USER2);
        AD2.setTitle("title2");
        AD2.setDescription("description2");
        AD2.setPrice(2000);
        AD2.setImage("/ads-image/2");

        AD_COMMENT = new CommentEntity();
        AD_COMMENT.setId(1);
        AD_COMMENT.setAuthor(USER);
        AD_COMMENT.setAd(AD);
        AD_COMMENT.setCreatedAt(localDateTimeToLong(LocalDateTime.of(2023, Month.JANUARY, 1, 12, 0, 0)));
        AD_COMMENT.setText("text");

        AVATAR_IMAGE = new AvatarEntity();
        AVATAR_IMAGE.setId(1);
        AVATAR_IMAGE.setFilePath(IMAGE_TEST_DIR + "\\" + AVATAR_FILE_NAME);
        AVATAR_IMAGE.setFileExtension("jpeg");
        AVATAR_IMAGE.setFileSize(83671);
        AVATAR_IMAGE.setMediaType("image/jpeg");
    }
}
