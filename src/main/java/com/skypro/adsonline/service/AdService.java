package com.skypro.adsonline.service;

import com.skypro.adsonline.dto.CreateAds;
import org.springframework.web.multipart.MultipartFile;

public interface AdService {

    boolean addAd(Object properties, MultipartFile image);

    boolean getAds(Integer id);

    boolean removeAd(Integer id);

    boolean updateAds(Integer id, CreateAds ads);

    boolean getAdsMe();

    boolean getAllAds();

    boolean updateImage(Integer id, MultipartFile image);
}
