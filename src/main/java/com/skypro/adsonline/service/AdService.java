package com.skypro.adsonline.service;

import com.skypro.adsonline.dto.Ads;
import com.skypro.adsonline.dto.Comment;
import com.skypro.adsonline.dto.CreateAds;

public interface AdService {

    boolean addAds(Object properties, byte[] image);

    boolean getComments(String adPk);

    boolean getComments(String adPk, Integer id);

    boolean addComments(String adPk, Comment comment);

    boolean addComments(long id, String adPk);

    boolean getFullAd(Integer id);

    boolean removeAds(Integer id);

    boolean updateAds(long id, CreateAds ads);

    boolean deleteComments(Integer id, String adPk);

    boolean updateComments(Integer id, String adPk, Comment comment);

    boolean getAdsMe();
}
