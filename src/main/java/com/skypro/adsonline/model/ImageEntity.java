package com.skypro.adsonline.model;

import com.skypro.adsonline.enums.ImageType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "images")
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private ImageType imageType;
    private String filePath;
    private String fileExtension;
    private long fileSize;
    private String mediaType;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_images_users"))
    private UserEntity user;

}
