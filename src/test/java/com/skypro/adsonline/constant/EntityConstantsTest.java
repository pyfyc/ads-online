package com.skypro.adsonline.constant;

import com.skypro.adsonline.dto.Role;
import com.skypro.adsonline.model.AdEntity;
import com.skypro.adsonline.model.AdImageEntity;
import com.skypro.adsonline.model.CommentEntity;
import com.skypro.adsonline.model.UserEntity;

import java.time.LocalDateTime;
import java.time.Month;

import static com.skypro.adsonline.constant.SecurityConstantsTest.*;
import static com.skypro.adsonline.utils.DateUtils.localDateTimeToLong;

public class EntityConstantsTest {
    public static final UserEntity USER;
    public static final UserEntity USER2;
    public static final AdEntity AD;
    public static final AdEntity AD2;
    public static final CommentEntity AD_COMMENT;
    public static final AdImageEntity AD_IMAGE;

    //public static final Avatar AVATAR_IMAGE;

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

        AD_IMAGE = new AdImageEntity();
        AD_IMAGE.setId(1);
        AD_IMAGE.setFilePath("ads-image\\1-1.jpeg");
        AD_IMAGE.setFileExtension("webp");
        AD_IMAGE.setFileSize(121464);
        AD_IMAGE.setMediaType("image/webp");

        AD = new AdEntity();
        AD.setId(1);
        AD.setAuthor(USER);
        AD.setTitle("title");
        AD.setDescription("description");
        AD.setPrice(1000);
        AD.setImage("/ads-image/1");
        AD.setImageEntity(AD_IMAGE);
        //List<Image> images = Collections.singletonList(new Image());
        //images.get(0).setId(1L);
        //byte[] imageBytes = new byte[] { 0x01, 0x02, 0x03 };
        //images.get(0).setImage(imageBytes);
        //ADS.setImages(images);

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

//
//        AVATAR_IMAGE = new Avatar();
//        AVATAR_IMAGE.setUser(USER);
//        AVATAR_IMAGE.setImage(imageBytes);
    }
}
