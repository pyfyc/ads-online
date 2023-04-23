package com.skypro.adsonline.dto;
import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperAds {
    private int count; // общее количество объявлений
    private List<Ads> results;
}
