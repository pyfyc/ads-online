package com.skypro.adsonline.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "avatars")
public class AvatarEntity implements ImageInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String filePath;
    private String fileExtension;
    private long fileSize;
    private String mediaType;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_avatars_users"))
    private UserEntity user;

    @Override
    public String getMediaType() {
        return mediaType;
    }

    @Override
    public long getFileSize() {
        return fileSize;
    }
}
