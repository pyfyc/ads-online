package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.dto.*;
import com.skypro.adsonline.exception.AdNotFoundException;
import com.skypro.adsonline.model.AdEntity;
import com.skypro.adsonline.repository.AdRepository;
import com.skypro.adsonline.security.SecurityUser;
import com.skypro.adsonline.service.AdService;
import com.skypro.adsonline.utils.AdMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.skypro.adsonline.constant.ErrorMessage.ACCESS_DENIED_MSG;
import static com.skypro.adsonline.constant.ErrorMessage.AD_NOT_FOUND_MSG;

@Service
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final AdMapper adMapper;

    public AdServiceImpl(AdRepository adRepository, AdMapper adMapper) {
        this.adRepository = adRepository;
        this.adMapper = adMapper;
    }

    @Override
    public Ads addAd(CreateAds properties, MultipartFile image, SecurityUser currentUser) {
        AdEntity ad = adMapper.mapToAdEntity(properties, image, currentUser.getUsername());
        adRepository.save(ad);
        return adMapper.mapToAdDto(ad);
    }

    @Override
    public FullAds getAds(Integer id) {
        AdEntity ad = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException(AD_NOT_FOUND_MSG.formatted(id)));
        return adMapper.mapToFullAdsDto(ad);
    }

    @Override
    public boolean removeAd(Integer id, SecurityUser currentUser) {
        checkPermission(id, currentUser);
        AdEntity ad = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException(AD_NOT_FOUND_MSG.formatted(id)));
        adRepository.delete(ad);
        return true;
    }

    @Override
    public boolean updateImage(Integer id, MultipartFile image) {
        return false;
    }

    @Override
    public ResponseWrapperAds getAdsMe(SecurityUser currentUser) {
        List<Ads> ads = adRepository.findByAuthor(currentUser.getUser()).stream()
                .map(ad -> adMapper.mapToAdDto(ad))
                .toList();
        return new ResponseWrapperAds(ads.size(), ads);
    }

    @Override
    public ResponseWrapperAds getAllAds() {
        List<Ads> ads = adRepository.findAll().stream()
                .map(ad -> adMapper.mapToAdDto(ad))
                .toList();
        return new ResponseWrapperAds(ads.size(), ads);
    }

    @Override
    public ResponseWrapperAds getAdsByTitleLike(String title) {
        List<Ads> ads = adRepository.
                findByTitleContainingIgnoreCase(title).stream()
                .map(ad -> adMapper.mapToAdDto(ad))
                .toList();
        return new ResponseWrapperAds(ads.size(), ads);
    }

    @Override
    public Ads updateAds(Integer id, CreateAds ads, SecurityUser currentUser) {

        AdEntity ad = adRepository.findById(id).orElseThrow(() ->
                new AdNotFoundException(AD_NOT_FOUND_MSG.formatted(id)));
        checkPermission(id, currentUser);

        ad.setTitle(ads.getTitle());
        ad.setDescription(ads.getDescription());
        ad.setPrice(ads.getPrice());

        adRepository.save(ad);
        return adMapper.mapToAdDto(ad);
    }
    private void checkPermission(Integer adId, SecurityUser currentUser) {
        Integer authorId = adRepository
                .findById(adId)
                .orElseThrow(() ->
                        new AdNotFoundException(AD_NOT_FOUND_MSG.formatted(adId)))
                .getAuthor()
                .getId();
        if (!authorId.equals(currentUser.getUser().getId())
                && !currentUser.getAuthorities().
                contains(new SimpleGrantedAuthority(Role.ADMIN.name()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ACCESS_DENIED_MSG);
        }
    }
}
