package com.skypro.adsonline.dto;

import lombok.Data;

// @Entity
@Data
public class Image {
    private long id;
    private String name;
    private String originalFileName;
    private long size;
    private String contentType;
    private boolean previewImage;
    // @Lob
    private byte[] bytes;
    // @ManyToOne
    private Ads ads;
}
