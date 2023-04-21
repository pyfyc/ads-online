package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageServiceImpl implements ImageService {

    @Override
    public boolean updateAdsImage(int adsId, MultipartFile file) {
        return true;
    }
}
