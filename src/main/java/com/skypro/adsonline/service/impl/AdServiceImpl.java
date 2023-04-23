package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.dto.CreateAds;
import com.skypro.adsonline.service.AdService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AdServiceImpl implements AdService {

    @Override
    public boolean addAd(Object properties, MultipartFile image) {
        return false;
    }

    @Override
    public boolean getAds(Integer id) {
        return false;
    }

    @Override
    public boolean removeAd(Integer id) {
        return false;
    }

    @Override
    public boolean updateImage(Integer id, MultipartFile image) {
        return false;
    }

    @Override
    public boolean getAdsMe() {
        return false;
    }

    @Override
    public boolean getAllAds() {
        return false;
    }

    @Override
    public boolean updateAds(Integer id, CreateAds ads) {
        return false;
    }
}
