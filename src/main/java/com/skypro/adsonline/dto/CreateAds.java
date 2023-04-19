package com.skypro.adsonline.dto;

import lombok.Data;

@Data
public class CreateAds {
    private String description;
    private int price;
    private String title;
}
