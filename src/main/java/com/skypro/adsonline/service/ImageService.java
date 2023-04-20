package com.skypro.adsonline.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    boolean updateAdsImage(int adsId, MultipartFile file);

}
