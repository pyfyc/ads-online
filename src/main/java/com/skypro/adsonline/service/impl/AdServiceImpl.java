package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.dto.*;
import com.skypro.adsonline.exception.AdNotFoundException;
import com.skypro.adsonline.model.AdEntity;
import com.skypro.adsonline.model.UserEntity;
import com.skypro.adsonline.repository.AdRepository;
import com.skypro.adsonline.repository.UserRepository;
import com.skypro.adsonline.service.AdService;
import com.skypro.adsonline.service.UserService;
import com.skypro.adsonline.utils.AdMapper;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.skypro.adsonline.constant.ErrorMessage.ACCESS_DENIED_MSG;
import static com.skypro.adsonline.constant.ErrorMessage.AD_NOT_FOUND_MSG;

@Service
@Transactional
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final AdMapper adMapper;

    public AdServiceImpl(AdRepository adRepository, UserRepository userRepository, AdMapper adMapper, UserService userService) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.adMapper = adMapper;
        this.userService = userService;
    }

    @Override
    public Ads addAd(CreateAds properties, MultipartFile image, UserDetails currentUser) {
        AdEntity ad = adMapper.mapToAdEntity(properties, image, currentUser.getUsername());
        ad = adRepository.save(ad);
        return adMapper.mapToAdDto(ad);
    }

    @Override
    public FullAds getAds(Integer id) {
        AdEntity ad = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException(AD_NOT_FOUND_MSG.formatted(id)));
        return adMapper.mapToFullAdsDto(ad);
    }

    @Override
    public boolean removeAd(Integer id, UserDetails currentUser) {
        checkPermission(id, currentUser);
        AdEntity ad = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException(AD_NOT_FOUND_MSG.formatted(id)));
        adRepository.delete(ad);
        return true;
    }

    @Override
    public boolean updateImage(Integer id, MultipartFile image, UserDetails currentUser) {
        checkPermission(id, currentUser);
        return true;
    }

    @Override
    public ResponseWrapperAds getAdsMe(UserDetails currentUser) {
        UserEntity author = userService.checkUserByUsername(currentUser.getUsername());
        List<Ads> ads = adRepository.findByAuthor(author).stream()
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
        List<Ads> ads = adRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(ad -> adMapper.mapToAdDto(ad))
                .toList();
        return new ResponseWrapperAds(ads.size(), ads);
    }

    @Override
    public Ads updateAds(Integer id, CreateAds ads, UserDetails currentUser) {
        checkPermission(id, currentUser);
        AdEntity ad = adRepository.findById(id).orElseThrow(() ->
                new AdNotFoundException(AD_NOT_FOUND_MSG.formatted(id)));

        ad.setTitle(ads.getTitle());
        ad.setDescription(ads.getDescription());
        ad.setPrice(ads.getPrice());

        adRepository.save(ad);
        return adMapper.mapToAdDto(ad);
    }

    /**
     * Checks if the author of the ad is the same as logged-in user.
     * If it is not and the user is not an ADMIN then throw an exception.
     * @param adId ad id
     * @param currentUser logged-in user
     */
    private void checkPermission(Integer adId, UserDetails currentUser) {
        String authorUsername = adRepository
                .findById(adId)
                .orElseThrow(() -> new AdNotFoundException(AD_NOT_FOUND_MSG.formatted(adId)))
                .getAuthor()
                .getUsername();
        if (!authorUsername.equals(currentUser.getUsername())
                && !currentUser.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.name()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ACCESS_DENIED_MSG.formatted(currentUser.getUsername()));
        }
    }
}
