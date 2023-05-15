package com.skypro.adsonline.constant;

import com.skypro.adsonline.model.AdEntity;
import com.skypro.adsonline.model.CommentEntity;
import com.skypro.adsonline.model.UserEntity;

import java.time.LocalDateTime;
import java.time.Month;

import static com.skypro.adsonline.constant.SecurityConstantsTest.SECURITY_USER_NAME;
import static com.skypro.adsonline.constant.SecurityConstantsTest.SECURITY_USER_PASSWORD;
import static com.skypro.adsonline.utils.DateUtils.localDateTimeToLong;

public class EntityConstantsTest {
    public static final UserEntity USER;

    public static final AdEntity ADS;

    public static final CommentEntity ADS_COMMENT;

    //public static final Image IMAGE;

    //public static final Avatar AVATAR_IMAGE;

    static {
        USER = new UserEntity();
        USER.setId(1);
        USER.setFirstName("firstName");
        USER.setLastName("lastName");
        USER.setPhone("phone");
        USER.setUsername(SECURITY_USER_NAME);
        USER.setPassword(SECURITY_USER_PASSWORD);
        //USER.setEnabled(true);

        ADS = new AdEntity();
        ADS.setId(1);
        ADS.setAuthor(USER);
        ADS.setTitle("title");
        ADS.setDescription("description");
        ADS.setPrice(1000);
        //List<Image> images = Collections.singletonList(new Image());
        //images.get(0).setId(1L);
        //byte[] imageBytes = new byte[] { 0x01, 0x02, 0x03 };
        //images.get(0).setImage(imageBytes);
        //ADS.setImages(images);

        ADS_COMMENT = new CommentEntity();
        ADS_COMMENT.setId(1);
        ADS_COMMENT.setAuthor(USER);
        ADS_COMMENT.setAd(ADS);
        ADS_COMMENT.setCreatedAt(localDateTimeToLong(LocalDateTime.of(2023, Month.JANUARY, 1, 12, 0, 0)));
        ADS_COMMENT.setText("text");

//        IMAGE = new Image();
//        IMAGE.setId(1L);
//        IMAGE.setImage(imageBytes);
//        IMAGE.setAds(ADS);
//
//        AVATAR_IMAGE = new Avatar();
//        AVATAR_IMAGE.setUser(USER);
//        AVATAR_IMAGE.setImage(imageBytes);
    }
}
