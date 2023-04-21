package com.skypro.adsonline.service;

import com.skypro.adsonline.dto.Ads;
import com.skypro.adsonline.dto.Comment;

public interface AdService {

    boolean addAds(Ads ads);

    boolean getComments(long id);

    boolean getComments(Comment comment);

    boolean addComments(Comment comment);

    boolean getFullAd(long id);

    boolean removeAds(long id);

    boolean updateAds(long id, Ads ads);

    boolean deleteComments(long id);

    boolean updateComments(long id);

    boolean getAdsMe();
}
