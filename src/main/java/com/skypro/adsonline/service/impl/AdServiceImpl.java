package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.dto.Comment;
import com.skypro.adsonline.dto.CreateAds;
import com.skypro.adsonline.service.AdService;
import org.springframework.stereotype.Service;

@Service
public class AdServiceImpl implements AdService {

    @Override
    public boolean addAds(Object properties, byte[] image) {
        return false;
    }

    @Override
    public boolean getComments(String adPk) {
        return false;
    }

    @Override
    public boolean getComments(String adPk, Integer id) {
        return false;
    }

    @Override
    public boolean addComments(String adPk, Comment comment) {
        return false;
    }

    @Override
    public boolean addComments(long id, String adPk) {
        return false;
    }

    @Override
    public boolean getFullAd(Integer id) {
        return false;
    }

    @Override
    public boolean removeAds(Integer id) {
        return false;
    }

    @Override
    public boolean updateAds(long id, CreateAds ads) {
        return false;
    }

    @Override
    public boolean deleteComments(Integer id, String adPk) {
        return false;
    }

    @Override
    public boolean updateComments(Integer id, String adPk, Comment comment) {
        return false;
    }

    @Override
    public boolean getAdsMe() {
        return false;
    }
}
